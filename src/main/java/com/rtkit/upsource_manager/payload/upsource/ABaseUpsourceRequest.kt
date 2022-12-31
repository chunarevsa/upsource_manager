package com.rtkit.upsource_manager.payload.upsource

import com.fasterxml.jackson.annotation.JsonIgnore
import com.rtkit.upsource_manager.config.AdminConfig.Companion.ADMIN_BASIC_AUTH
import com.rtkit.upsource_manager.payload.ABaseHTTPRequest
import com.rtkit.upsource_manager.payload.IMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

/**
 * Абстрактный класс для запросов в UPSOURCE
 */
abstract class ABaseUpsourceRequest<RESP : ABaseUpsourceResponse> : ABaseHTTPRequest<RESP>() {
    private val logger: Logger = LogManager.getLogger(ABaseUpsourceRequest::class.java)

    @JsonIgnore
    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

    @JsonIgnore
    override fun getAuth(): String {
        return ADMIN_BASIC_AUTH
    }

    @JsonIgnore
    override fun getRequest(): String {
        return getMapper().writeValueAsString(this)
    }

    override fun process(): RESP {
        val responseString = doPostRequestAndReceiveResponse()
        val resp = getMapper().readValue(responseString, getActualResponseClass())
        if (resp != null) {
            resp.resultCode = httpCode
        }
        return resp
    }

}

