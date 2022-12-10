package com.rtkit.upsource_manager.payload.api.request

import com.rtkit.upsource_manager.entities.ERequest
import com.rtkit.upsource_manager.payload.api.IMapper
import com.rtkit.upsource_manager.payload.api.JsonObjectMapper
import com.rtkit.upsource_manager.payload.api.response.userInfo.UserInfoResponseDTO

class UserInfoRequestDTO(
    private val ids: String
) : ABaseUpsourceRequest<UserInfoResponseDTO>() {

    override fun getRequestURL(): String {
        return ERequest.USER_INFO.requestUrl
    }

    override fun getJsonRequest(): String {
        return "{\"ids\":\"$ids\"}" // TODO: Сделать генерацию
    }

    override fun getMapper(): IMapper {
        return JsonObjectMapper()
    }
}
