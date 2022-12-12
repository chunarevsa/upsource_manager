package com.rtkit.upsource_manager.services

import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.entities.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.request.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.response.userInfo.UserInfoResponseDTO

class FindUsersRequestDTO(
    @JsonProperty("pattern")
    /** Search query, e.g. part of the name */
    val pattern: String = "",
    @JsonProperty("limit")
    val limit: Int
) : ABaseUpsourceRequest<UserInfoResponseDTO>() {

    override fun getRequestURL(): String {
        return ERequest.FIND_USERS.requestUrl
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

}
