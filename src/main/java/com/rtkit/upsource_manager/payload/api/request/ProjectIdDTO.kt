package com.rtkit.upsource_manager.payload.api.request

import com.rtkit.upsource_manager.entities.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.response.projectInfo.ProjectInfoDTO

class ProjectIdDTO : ABaseUpsourceRequest<ProjectInfoDTO>() {

    override fun getRequestURL(): String {
        return ERequest.GET_PROJECT_INFO.requestUrl
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }

    // Подменяем кренделя админа на пользовательские
    fun setAuth(authData: String)  {
        basicAuth = authData
    }

}
