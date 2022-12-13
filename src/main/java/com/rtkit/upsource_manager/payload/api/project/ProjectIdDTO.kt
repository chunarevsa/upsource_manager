package com.rtkit.upsource_manager.payload.api.project

import com.rtkit.upsource_manager.payload.api.ERequest
import com.rtkit.upsource_manager.payload.api.ABaseUpsourceRequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper

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
