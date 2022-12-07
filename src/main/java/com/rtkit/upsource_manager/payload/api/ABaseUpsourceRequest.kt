package com.rtkit.upsource_manager.payload.api

import org.springframework.beans.factory.annotation.Value
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets

/**
 * Абстрактный класс для запросов в UPSOURCE
 */
abstract class ABaseUpsourceRequest<RESP : ABaseUpsourceResponse> {

    val projectId: String = "elk"

    private var headers = mutableMapOf<String, String>()

    abstract fun getRequestURL(): String
    abstract fun getResponse(): RESP
    abstract fun getJsonRequest(): String

    fun doPostRequestAndReceiveResponse(): String {
        val con = configureConnection()
        val response = StringBuilder()
        try {
            con.outputStream.use { os ->
                val input = getJsonRequest().toByteArray(StandardCharsets.UTF_8)
                os?.write(input, 0, input.size)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        try {
            BufferedReader(
                InputStreamReader(con.inputStream, StandardCharsets.UTF_8)
            ).use { br ->
                var responseLine: String?
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine?.trim { it <= ' ' })
                }
            }
        } catch (e: IOException) {
            println("Ошибка чтения информации")
            throw Exception("Ошибка чтения информации")
        }
        return response.toString()
    }

    private fun configureConnection(): HttpURLConnection {
        var con: HttpURLConnection? = null
        try {


            con = getHttpUrl().openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.doOutput = true
            con.setRequestProperty("Accept", "application/json")
            con.setRequestProperty("Authorization", encodedAuth)
            con.setRequestProperty("Content-Type", "application/json")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return con ?: throw Exception("con пустой")
    }

    private fun getHttpUrl(): URL {
        var url: URL? = null
        try {
            url = URL(getRequestURL())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return url ?: throw Exception("Пустой URL")
    }
}

