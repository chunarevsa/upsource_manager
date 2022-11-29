package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.RequestURL
import java.net.MalformedURLException
import java.net.URL

class TrialConnection : Connection {
    override val jsonRequest: String = "{\"projectId\": \"elk\"}"
    override val requestUrl: RequestURL = RequestURL.GET_PROJECT_INFO
    override val response: String? = null

    @Throws(MalformedURLException::class)
    override fun getUrl(): URL {
        return URL(requestUrl.toString())
    }
}
