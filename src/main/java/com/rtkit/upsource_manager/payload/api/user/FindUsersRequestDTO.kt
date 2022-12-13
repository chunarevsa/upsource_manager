package com.rtkit.upsource_manager.payload.api.user

import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.api.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceRequest

class FindUsersRequestDTO(
    @JsonProperty("pattern")
    /** Search query, e.g. part of the name ( "" - все )*/
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
