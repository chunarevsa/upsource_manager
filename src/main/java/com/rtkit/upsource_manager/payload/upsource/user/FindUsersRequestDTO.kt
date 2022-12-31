package com.rtkit.upsource_manager.payload.upsource.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest

class FindUsersRequestDTO(
    @JsonProperty("pattern")
    /** Search query, e.g. part of the name ( "" - все )*/
    val pattern: String = "",
    @JsonProperty("limit")
    val limit: Int
) : ABaseUpsourceRequest<UserInfoResponseDTO>() {

    override fun getRequestURL(): String {
        return EUpsourceRequest.FIND_USERS.requestUrl
    }

    override fun toString(): String {
        return "FindUsersRequestDTO(pattern='$pattern', limit=$limit)"
    }
}
