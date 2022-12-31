package com.rtkit.upsource_manager.payload.upsource.revision

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceResponse
import com.rtkit.upsource_manager.payload.upsource.changes.RevisionsDiffDTO

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class ReviewSummaryChangesResponseDTO : ABaseUpsourceResponse() {
    @JsonProperty("diff")
    var diff: RevisionsDiffDTO? = null

    @JsonProperty("annotation")
    var annotation: String? = null

    override fun toString(): String {
        return "ReviewSummaryChangesResponseDTO(diff=$diff, annotation=$annotation)"
    }
}
