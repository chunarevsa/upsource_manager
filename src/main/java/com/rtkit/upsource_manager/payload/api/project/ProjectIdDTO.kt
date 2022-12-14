package com.rtkit.upsource_manager.payload.api.project

import com.fasterxml.jackson.annotation.JsonProperty
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper

class ProjectIdDTO(
    @JsonProperty("projectId")
    val projectId: String
) : ABaseUpsourceRequest<ProjectInfoDTO>() {

    override fun getRequestURL(): String {
        return ERequest.GET_PROJECT_INFO.requestUrl
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

    // Подменяем кренделя админа на пользовательские
    fun setAuth(authData: String) {
        basicAuth = authData
    }

}
