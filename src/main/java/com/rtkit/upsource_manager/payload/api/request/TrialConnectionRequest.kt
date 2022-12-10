package com.rtkit.upsource_manager.payload.api.request

import com.rtkit.upsource_manager.entities.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.response.projectInfo.ProjectInfoDTO

class TrialConnectionRequest(
    private val authData: String
) : ABaseUpsourceRequest<ProjectInfoDTO>() {

    override fun getRequestURL(): String {
        basicAuth = authData
        return ERequest.GET_PROJECT_INFO.requestUrl
    }

    override fun getJsonRequest(): String {
        return "{\"projectId\": \"elk\"}"
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

    fun setAuth(authData: String)  {
        basicAuth = authData
    }

}
