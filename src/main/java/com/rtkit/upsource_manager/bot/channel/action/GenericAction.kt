package com.rtkit.upsource_manager.bot.channel.action

import com.rtkit.upsource_manager.IRequest
import com.rtkit.upsource_manager.bot.channel.APipelineActionInfo

class GenericAction(platform: String) : APipelineActionInfo<IRequest>(platform) {
    override suspend fun applyAction(request: IRequest): GenericAction {
        return this
    }
}
