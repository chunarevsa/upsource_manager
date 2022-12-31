package com.rtkit.upsource_manager.payload.upsource.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.upsource.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.upsource.EUpsourceRequest

class ProjectIdDTO(
    @JsonProperty("projectId")
    val projectId: String
) : ABaseUpsourceRequest<ProjectInfoDTO>() {

    override fun getRequestURL(): String {
        return EUpsourceRequest.GET_PROJECT_INFO.requestUrl
    }

    override fun toString(): String {
        return "ProjectIdDTO(projectId='$projectId')"
    }

}
