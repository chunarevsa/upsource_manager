package com.rtkit.upsource_manager.payload.upsource.user

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserInfoRequestDTO(
    @JsonProperty("ids")
    val ids: String
) : ABaseUpsourceRequest<UserInfoResponseDTO>() {

    override fun getRequestURL(): String {
        return EUpsourceRequest.USER_INFO.requestUrl
    }

    override fun toString(): String {
        return "UserInfoRequestDTO(ids='$ids')"
    }

}
