package com.rtkit.upsource_manager.payload.api

import com.rtkit.upsource_manager.entities.RequestURL

class TrialConnectionRequest(
    private val authData: String
) : ABaseUpsourceRequest<TrialConnectionResponse>() {

    override fun getRequestURL(): String {
        return RequestURL.GET_PROJECT_INFO.toString()
    }

    override fun getResponse(): TrialConnectionResponse {
        encodedAuth = authData
        return TrialConnectionResponse(doPostRequestAndReceiveResponse())
    }

    override fun getJsonRequest(): String {
        return "{\"projectId\": \"elk\"}"
    }

}
