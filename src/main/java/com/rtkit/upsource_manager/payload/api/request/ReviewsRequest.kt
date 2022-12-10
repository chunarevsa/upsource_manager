package com.rtkit.upsource_manager.payload.api.request

import com.rtkit.upsource_manager.entities.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.response.reviewList.ReviewListDTO

class ReviewsRequest(
    val limit: Int? = null,
    val query: String? = null,
    val sortBy: String? = null,
    val skip: Int? = null

) : ABaseUpsourceRequest<ReviewListDTO>() {
    override fun getRequestURL(): String {
        return ERequest.GET_REVIEWS.requestUrl
    }

    override fun getJsonRequest(): String {
        // TODO: вставить генератор строки
        // пока просто подставляем готовую
        return "{\"limit\": 20, \"sortBy\": \"id,desc\"}"
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

}
