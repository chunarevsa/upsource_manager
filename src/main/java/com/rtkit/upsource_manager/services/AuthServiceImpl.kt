package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.entities.RequestURL
import org.springframework.stereotype.Service
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.*

@Service
class AuthServiceImpl {

    var authFile = File("auth.txt")
    var isAuthenticated: Boolean = false

    fun validateAuthFile(): Boolean {
        if (!authFile.exists()) {
            authFile.createNewFile()
            println("Создан новый файл ${authFile.name}")
            return false
        }
        return true
    }

    fun validateCon(): Boolean {
        val jsonRequest = "{\"projectId\": \"elk\"}"
        val authCon = getConnection(RequestURL.GET_PROJECT_INFO)
        val response: String? = doPostRequestAndReceiveResponse(authCon, jsonRequest)
        if (response == null) {
            println("Не верный логин или пароль")
            return false
        }
        this.isAuthenticated = true
        println("Успешная авторизация")
        return true
    }

    fun login(username: String, password: String): Boolean {

        val authData = "$username:$password"
        val encodeAuthData = String(Base64.getEncoder().encode(authData.toByteArray()))

        authFile.writeText(encodeAuthData)
        return validateCon()
    }

    fun getConnection(requestURL: RequestURL): HttpURLConnection {
        var url: URL? = null
        try {
            url = URL(requestURL.toString())
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        }
        return configureConnection(url)
    }

    private fun configureConnection(url: URL?): HttpURLConnection {
        var con: HttpURLConnection? = null
        try {
            val br = BufferedReader(FileReader(authFile))
            val encodeAuthData = br.readLine()
            br.close()
            val basicAuth = "Basic $encodeAuthData"
            con = url?.openConnection() as HttpURLConnection
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

    fun doPostRequestAndReceiveResponse(con: HttpURLConnection, jsonRequest: String): String? {
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
                    InputStreamReader(con.inputStream, StandardCharsets.UTF_8)).use { br ->
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

    fun logout() {
        authFile.delete()
        isAuthenticated = false
        println("Успешный выход")
    }
}