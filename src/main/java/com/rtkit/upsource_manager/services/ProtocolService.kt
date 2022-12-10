package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.payload.api.request.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.response.ABaseUpsourceResponse
import org.springframework.stereotype.Service

@Service
class ProtocolService {
    fun <REQ : ABaseUpsourceRequest<RESP>, RESP : ABaseUpsourceResponse> makeRequest(request: REQ): RESP? {
        return request.process()
    }
}
