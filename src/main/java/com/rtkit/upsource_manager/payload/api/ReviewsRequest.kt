package com.rtkit.upsource_manager.payload.api

import com.rtkit.upsource_manager.entities.RequestURL

class ReviewsRequest(
    val limit: Int? = null,
    val query: String? = null,
    val sortBy: String? = null,
    val skip: Int? = null

) : ABaseUpsourceRequest<ReviewResponse>() {

    override fun getRequestURL(): String {
        return RequestURL.GET_REVIEWS.toString()
    }

    override fun getResponse(): ReviewResponse {
        return ReviewResponse(doPostRequestAndReceiveResponse())
    }

    override fun getJsonRequest(): String {
        // TODO: вставить генератор строки
        // пока просто подставляем готовую
        return "{\"limit\": 20, \"sortBy\": \"id,desc\"}"
    }

}
