package com.rtkit.upsource_manager.payload.api.request

import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.entities.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.response.reviewList.ReviewListDTO

class ReviewsRequest(
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
