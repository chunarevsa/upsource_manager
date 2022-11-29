package com.rtkit.upsource_manager.services

import org.springframework.stereotype.Service
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.StandardCharsets

@Service
class ConnectionService {
    fun makeTrialConnection(basicAuth: String) {
        val connectionFactory: ConnectionFactory = TrialConnectionFactory()
        val connection: Connection = connectionFactory.getConnection()
        val con: HttpURLConnection = configureConnection(connection.getUrl(), basicAuth)
        doPostRequestAndGetResponse(con, connection.jsonRequest)
    }


    private fun configureConnection(url: URL, basicAuth: String): HttpURLConnection {
        var con: HttpURLConnection? = null
        try {
            con = url.openConnection() as HttpURLConnection
            con.requestMethod = "POST"
            con.doOutput = true
            con.setRequestProperty("Accept", "application/json")
            con.setRequestProperty("Authorization", basicAuth)
            con.setRequestProperty("Content-Type", "application/json")
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return con!!
    }

    private fun doPostRequestAndGetResponse(con: HttpURLConnection, jsonRequest: String): String? {
        val response = StringBuilder()
        try {
            con.outputStream.use { os ->
                val input = jsonRequest.toByteArray(StandardCharsets.UTF_8)
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
            return null
        }
        return response.toString()
    }


}
