package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.ABaseHTTPRequest
import com.rtkit.upsource_manager.payload.ABaseHTTPResponse
import org.springframework.stereotype.Service

@Service
class ProtocolService {
    fun <REQ : ABaseHTTPRequest<RESP>, RESP : ABaseHTTPResponse> makeRequest(request: REQ): RESP {
        return request.process()
    }
}
