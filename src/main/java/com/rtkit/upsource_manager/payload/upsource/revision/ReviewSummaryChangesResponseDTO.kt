package com.rtkit.upsource_manager.payload.upsource.revision

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ReviewSummaryChangesResponseDTO : ABaseUpsourceResponse() {

    @JsonProperty("annotation")
    var annotation: String? = null

    override fun toString(): String {
        return "ReviewSummaryChangesResponseDTO(annotation=$annotation)"
    }
}
