package com.rtkit.upsource_manager.payload.api

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper

open class JsonObjectMapper : IMapper {

    private val mapper: ObjectMapper = ObjectMapper()

    override fun writeValueAsString(value: IMappable?): String {
        return try {
            mapper!!.writeValueAsString(value)
        } catch (e: Exception) {
            throw Exception(e) // TODO: Exception(e)
        }
    }

    override fun <T : IMappable?> readValue(content: String?, valueType: Class<T>?): T {
        mapper.enable(DeserializationFeature.UNWRAP_ROOT_VALUE)
        return mapper!!.readValue(content, valueType)
    }

}
