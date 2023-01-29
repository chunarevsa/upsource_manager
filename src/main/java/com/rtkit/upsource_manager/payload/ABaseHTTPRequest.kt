package com.rtkit.upsource_manager.payload

import com.fasterxml.jackson.annotation.JsonIgnore
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.ConcurrentHashMap

/**
 * Абстрактный класс для запросов в UPSOURCE
 */
abstract class ABaseHTTPRequest<RESP : ABaseHTTPResponse> :
    IMappable {
    private val logger: Logger = LogManager.getLogger(ABaseHTTPRequest::class.java)

    @JsonIgnore
    abstract fun getRequestURL(): String
    abstract fun getRequest(): String

    @JsonIgnore
    abstract fun getMapper(): IMapper

    @JsonIgnore
    abstract fun getAuth(): String

    @JsonIgnore
    abstract fun process(): RESP

    abstract override fun toString(): String

    private val reqRespClassMapping = ConcurrentHashMap<Class<out ABaseHTTPRequest<RESP>>, Class<out RESP>>()

    @JsonIgnore
    protected var httpCode: Int = 0

    @JsonIgnore
    private var auth: String? = ""

    @JsonIgnore
    private var headers = mutableMapOf<String, String>()

    protected fun doPostRequestAndReceiveResponse(): String {
        val con = configureConnection()
        val response = StringBuilder()
        try {
            val req = getRequest()
            con.outputStream.use { os ->
                val input = req.toByteArray(StandardCharsets.UTF_8)
                os?.write(input, 0, input.size)
            }
        } catch (e: IOException) {
            logger.error("Ошибка запроса")
            e.printStackTrace()
        }
        try {
            BufferedReader(
                InputStreamReader(con.inputStream, StandardCharsets.UTF_8)
            ).use { br ->
                var responseLine: String?
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine?.trim { it <= ' ' })
                }
            }
        } catch (e: IOException) {
            logger.error("Ошибка чтения информации")
            throw e
        }
        if (response.toString().isNotEmpty()) this.httpCode = con.responseCode
        return response.toString()
    }

    private fun configureConnection(): HttpURLConnection {
        var con: HttpURLConnection? = null
        try {
            con = getHttpUrl().openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.doOutput = true
            con.setRequestProperty("Accept", "application/json")
            con.setRequestProperty("Authorization", getAuth())
            con.setRequestProperty("Content-Type", "application/json")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return con ?: throw Exception("con пустой")
    }

    private fun getHttpUrl(): URL {
        var url: URL? = null
        try {
            url = URL(getRequestURL())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return url ?: throw Exception("Пустой URL")
    }

    protected open fun getActualResponseClass(): Class<out RESP> {
        if (reqRespClassMapping.containsKey(javaClass)) {
            return reqRespClassMapping[javaClass]!!
        }

        val errorMessage =
            "Не указан дженерик ответа в объявлении класса '" + javaClass.simpleName + "', пожалуйста, исправьте это."
        val type: Type = getGenericParameterClass(errorMessage)
        val arguments = (type as ParameterizedType).actualTypeArguments

        if (arguments.size > 0) {
            for (argument in arguments) {
                if (argument is ParameterizedType) {
                    val respClass = argument.rawType as Class<out RESP?>
                    reqRespClassMapping[javaClass] = respClass
                    return respClass
                } else if (ABaseHTTPResponse::class.java.isAssignableFrom(argument as Class<*>)) {
                    val respClass = argument as Class<out RESP?>
                    reqRespClassMapping[javaClass] = respClass
                    return respClass
                }
            }
        }
        throw java.lang.IllegalArgumentException(errorMessage)
    }


    open fun getGenericParameterClass(errorMessage: String): Type {
        // clazz - текущий рассматриваемый класс
        var clazz: Class<*> = this.javaClass
        var genericSuperclass = clazz.genericSuperclass
        val genericClass: Class<*> = ParameterizedType::class.java
        // Прекращаем работу если genericClass не является предком clazz.
        require(
            !(!genericClass.isAssignableFrom(genericSuperclass.javaClass) &&
                    !genericClass.isAssignableFrom((genericSuperclass as Class<*>).genericSuperclass.javaClass))
        ) {
            errorMessage
        }
        while (true) {
            try {
                genericSuperclass = clazz.genericSuperclass
                clazz = if (genericSuperclass is ParameterizedType) {
                    return genericSuperclass
                } else {
                    genericSuperclass as Class<*>
                }
            } catch (e: Exception) {
                throw IllegalArgumentException(errorMessage)
            }
        }
    }

}

