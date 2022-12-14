package com.rtkit.upsource_manager.payload.api.user

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.api.ERequest
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper

@JsonInclude(JsonInclude.Include.NON_NULL)
class UserInfoRequestDTO(
    @JsonProperty("ids")
    val ids: String
) : ABaseUpsourceRequest<UserInfoResponseDTO>() {

    override fun getRequestURL(): String {
        return ERequest.USER_INFO.requestUrl
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

    override fun toString(): String {
        return "UserInfoRequestDTO(ids='$ids')"
    }

}