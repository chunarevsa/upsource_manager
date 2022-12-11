package com.rtkit.upsource_manager.payload.api.request

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.config.AdminConfig.Companion.ADMIN_BASIC_AUTH
import com.rtkit.upsource_manager.payload.api.IMappable
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.response.ABaseUpsourceResponse
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
abstract class ABaseUpsourceRequest<RESP : ABaseUpsourceResponse> : IMappable {

    @JsonProperty("projectId")
    val projectId: String = "elk"

    @JsonIgnore
    private var headers = mutableMapOf<String, String>()

    @JsonIgnore
    var basicAuth: String = ADMIN_BASIC_AUTH

    @JsonIgnore
    abstract fun getRequestURL(): String

    private fun getJsonRequest(): String {
        return getMapper().writeValueAsString(this)
    }

    protected abstract fun getMapper(): IMapper

    fun process(): RESP? {
        val responseString = doPostRequestAndReceiveResponse()
        return getMapper().readValue(responseString, getActualResponseClass());
    }

    private val reqRespClassMapping = ConcurrentHashMap<Class<out ABaseUpsourceRequest<RESP>>, Class<out RESP>>()

    protected open fun getActualResponseClass(): Class<out RESP?>? {
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
                } else if (ABaseUpsourceResponse::class.java.isAssignableFrom(argument as Class<*>)) {
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

    private fun doPostRequestAndReceiveResponse(): String {
        val con = configureConnection()
        val response = StringBuilder()
        try {
            con.outputStream.use { os ->
                val req = getJsonRequest()
                val input = req.toByteArray(StandardCharsets.UTF_8)
                os?.write(input, 0, input.size)
            }
        } catch (e: IOException) {
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
            println("Ошибка чтения информации")
            throw Exception("Ошибка чтения информации")
        }
        return response.toString()
    }

    private fun configureConnection(): HttpURLConnection {
        var con: HttpURLConnection? = null
        try {
            con = getHttpUrl().openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.doOutput = true
            con.setRequestProperty("Accept", "application/json")
            con.setRequestProperty("Authorization", basicAuth)
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

}

