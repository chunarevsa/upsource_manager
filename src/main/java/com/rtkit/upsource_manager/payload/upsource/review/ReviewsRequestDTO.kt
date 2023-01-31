package com.rtkit.upsource_manager.payload.upsource.review

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
class ReviewsRequestDTO(
    @JsonProperty("limit")
    val limit: Int,
    @JsonProperty("query")
    val query: String? = "state:open",
    @JsonProperty("sortBy")
    val sortBy: String = "updated",
    @JsonProperty("skip")
    val skip: Int? = null

) : ABaseUpsourceRequest<ReviewListDTO>() {

    override fun getRequestURL(): String {
        return EUpsourceRequest.GET_REVIEWS.requestUrl
    }

    override fun toString(): String {
        return "ReviewsRequestDTO(limit=$limit, query=$query, sortBy='$sortBy', skip=$skip)"
    }


}
