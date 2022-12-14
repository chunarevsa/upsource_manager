package com.rtkit.upsource_manager.payload.api.review

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonInclude
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceResponse

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
class CloseReviewResponseDTO: ABaseUpsourceResponse()