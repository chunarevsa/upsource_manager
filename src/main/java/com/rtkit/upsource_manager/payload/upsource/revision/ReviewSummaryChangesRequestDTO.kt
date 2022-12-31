package com.rtkit.upsource_manager.payload.upsource.revision

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.review.ReviewId

@JsonInclude(JsonInclude.Include.NON_NULL)
class ReviewSummaryChangesRequestDTO(
    @JsonProperty("ReviewIdDTO")
    var reviewId: ReviewId,

//    @JsonProperty("RevisionsSetDTO")
//    var revisions: RevisionsSetDTO = null
) : ABaseUpsourceRequest<ReviewSummaryChangesResponseDTO>() {
    override fun getRequestURL(): String {
        return EUpsourceRequest.GET_SUM_CHANGES.requestUrl
    }

    override fun toString(): String {
        return "ReviewSummaryChangesRequestDTO(reviewId=$reviewId)"
    }
}