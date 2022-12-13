package com.rtkit.upsource_manager.payload.api.review

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.api.ERequest
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper

@JsonInclude(JsonInclude.Include.NON_NULL)
class ReviewsRequestDTO(
    @JsonProperty("limit")
    val limit: Int,
    @JsonProperty("query")
    val query: String? = null,
    @JsonProperty("sortBy")
    val sortBy: String = "id,desc",
    @JsonProperty("skip")
    val skip: Int? = null

) : ABaseUpsourceRequest<ReviewListDTO>() {

    override fun getRequestURL(): String {
        return ERequest.GET_REVIEWS.requestUrl
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }
}
