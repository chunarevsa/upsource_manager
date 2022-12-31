package com.rtkit.upsource_manager.payload.upsource

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.rtkit.upsource_manager.payload.IMappable
import com.rtkit.upsource_manager.payload.IMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

open class JsonObjectMapper : IMapper {
    private val logger: Logger = LogManager.getLogger(JsonObjectMapper::class.java)

    private val mapper: ObjectMapper = ObjectMapper()

    override fun writeValueAsString(value: IMappable): String {
        return try {
            mapper.writeValueAsString(value)
        } catch (e: Exception) {
            logger.error("Ошибка записи реквеста: $value")
            e.printStackTrace()
            throw Exception(e) // TODO: Exception(e)
        }
    }

    override fun <T : IMappable> readValue(content: String, valueType: Class<T>): T {
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
        return mapper.readValue(content, valueType)
    }

}
