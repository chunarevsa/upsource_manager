package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.RequestURL
import java.net.URL

interface Connection {
    val jsonRequest: String
    val requestUrl: RequestURL
    val response: String?

    fun getUrl(): URL
}