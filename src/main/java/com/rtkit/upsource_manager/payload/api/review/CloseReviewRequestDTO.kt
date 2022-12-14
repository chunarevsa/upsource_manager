package com.rtkit.upsource_manager.payload.api.review

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper

@JsonInclude(JsonInclude.Include.NON_NULL)
data class CloseReviewRequestDTO(
    @JsonProperty("ReviewIdDTO")
    var reviewId: ReviewId,
    @JsonProperty("isFlagged")
    val isFlagged: Boolean = true
) : ABaseUpsourceRequest<CloseReviewResponseDTO>() {

    override fun getRequestURL(): String {
        return ERequest.CLOSE_REVIEW.requestUrl
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }
}