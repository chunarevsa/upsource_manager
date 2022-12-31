package com.rtkit.upsource_manager.payload.upsource.review

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest

@JsonInclude(JsonInclude.Include.NON_NULL)
class CloseReviewRequestDTO(
    @JsonProperty("reviewId")
    var reviewId: ReviewId,

    @JsonProperty("isFlagged")
    @get:JsonProperty("isFlagged")
    val isFlagged: Boolean = true
) : ABaseUpsourceRequest<CloseReviewResponseDTO>() {

    override fun getRequestURL(): String {
        return EUpsourceRequest.CLOSE_REVIEW.requestUrl
    }

    override fun toString(): String {
        return "CloseReviewRequestDTO(reviewId=$reviewId, isFlagged=$isFlagged)"
    }
}