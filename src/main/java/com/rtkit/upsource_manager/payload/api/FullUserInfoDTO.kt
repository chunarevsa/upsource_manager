package com.rtkit.upsource_manager.payload.api

import com.rtkit.upsource_manager.entities.RequestURL

class FullUserInfoDTO(
    private val userId: String
) : ABaseUpsourceRequest<UserInfoResponseDTO>() {

    override fun getRequestURL(): String {
        return RequestURL.USER_INFO.toString()
    }

    override fun getResponse(): UserInfoResponseDTO {
        return UserInfoResponseDTO(doPostRequestAndReceiveResponse())
    }

    override fun getJsonRequest(): String {
        return "{\"ids\":\"$userId\"}"
    }

}
