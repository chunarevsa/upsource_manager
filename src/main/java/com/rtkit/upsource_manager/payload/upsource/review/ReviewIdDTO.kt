package com.rtkit.upsource_manager.payload.upsource.review

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
class ReviewIdDTO(
    @JsonProperty("projectId")
    val projectId: String,

    @JsonProperty("reviewId")
    val reviewId: String

) : ABaseUpsourceRequest<ReviewDescriptorDTO>() {

    override fun getRequestURL(): String {
        return EUpsourceRequest.GET_REVIEW_DETAILS.requestUrl
    }

    override fun toString(): String {
        return "ReviewIdDTO(" +
                "projectId=$projectId, " +
                "reviewId=$reviewId)"
    }
}