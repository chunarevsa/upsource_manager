package com.rtkit.upsource_manager.events.upsource

import com.rtkit.upsource_manager.payload.ABaseHTTPResponse
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.context.ApplicationEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.util.concurrent.ConcurrentHashMap

@Component
abstract class AUpsourceListener<EVENT : AUpsourceEvent> : ApplicationListener<EVENT> {
    val logger: Logger = LogManager.getLogger(getActualResponseClass()::class.java)

    private val upsourceEventClassMapping = ConcurrentHashMap<Class<out AUpsourceListener<EVENT>>, Class<out EVENT>>()

    protected fun getActualResponseClass(): Class<out EVENT> {
        if (upsourceEventClassMapping.containsKey(javaClass)) {
            return upsourceEventClassMapping[javaClass]!!
        }

        val errorMessage =
            "Не указан дженерик ответа в объявлении класса '" + javaClass.simpleName + "', пожалуйста, исправьте это."
        val type: Type = getGenericParameterClass(errorMessage)
        val arguments = (type as ParameterizedType).actualTypeArguments

        if (arguments.size > 0) {
            for (argument in arguments) {
                if (argument is ParameterizedType) {
                    val respClass = argument.rawType as Class<out EVENT?>
                    upsourceEventClassMapping[javaClass] = respClass
                    return respClass
                } else if (ABaseHTTPResponse::class.java.isAssignableFrom(argument as Class<*>)) {
                    val respClass = argument as Class<out EVENT?>
                    upsourceEventClassMapping[javaClass] = respClass
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

abstract class AUpsourceEvent(vararg: Any) : ApplicationEvent(vararg)
