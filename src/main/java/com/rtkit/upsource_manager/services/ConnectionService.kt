package com.rtkit.upsource_manager.services

import org.springframework.stereotype.Service

@Service
class ConnectionService {
    fun makeTrialConnection(authData: String): Boolean {
        return makeRequest(TrialConnectionRequest(authData)).isSuccessful()
    }

    fun <REQ : ABaseUpsourceRequest<RESP>, RESP : ABaseUpsourceResponse> makeRequest(request: REQ): RESP {
        return request.getResponse()
    }
}
