package com.rtkit.upsource_manager.payload.api

import com.rtkit.upsource_manager.entities.RequestURL

class UserRequest(
    private val userId: String
) : ABaseUpsourceRequest<UserResponse>() {

    override fun getRequestURL(): String {
        return RequestURL.USER_INFO.toString()
    }

    override fun getResponse(): UserResponse {
        return UserResponse(doPostRequestAndReceiveResponse())
    }

    override fun getJsonRequest(): String {
        return "{\"ids\":\"$userId\"}"
    }

}
