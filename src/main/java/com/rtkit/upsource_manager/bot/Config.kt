package com.rtkit.upsource_manager.bot

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.rtkit.upsource_manager.bot.enums.EEmoji
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

@JsonIgnoreProperties(ignoreUnknown = true)
object Config : IResponse {
    private val logger: Logger = LogManager.getLogger(Config::class.java)

    /**  */
    @get:JsonProperty
    var botToken: String = ""

    @get:JsonProperty
    var channels = ChannelIdStore()

    @get:JsonProperty
    var channelStorage: MutableMap<String, ChannelStorage> = HashMap()

    /** Интро сообщение в основном канале */
    var introMessage: String = "Привет! \n" +
            "Здесь ты можешь управлять своими ревью из Upsource!\n" +
            "Чтобы отменить ревью нажми " + EEmoji.BLOCK.emoji

    /** Хранение маппинга пользователей гита на пользователей дискорда */
    @get:JsonProperty
    var userMapping: MutableMap<String, MutableSet<String>> = HashMap()
// /bot-git-user action:Привязать пользователя git-user:johnson discord-user:@Johnson1893#1084
// /bot-upsource-user action:Привязать пользователя upsource-user:chunarev discord-user:@Сергей Чунарёв#0923

    @get:JsonProperty
    var upsourceUserLogin = HashSet<String>()

    private val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
    private fun getConfigFile() =
        File(System.getProperty("user.home") + File.separator, "dartit-discord-bot-config.json")

    fun save() {
        val file = getConfigFile()
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, Config)
        logger.info("Config saved: ${file.absoluteFile}")
    }

    fun load() {
        val file = getConfigFile()
        if (file.exists()) {
            logger.info("Reading config file ${file.absoluteFile}")
            mapper.readValue(file, this::class.java)
        }
        save()
    }

    fun addUpsourceUserLogin(login: String) {
        upsourceUserLogin.add(login)
        save()
    }
}

class ChannelIdStore {
    @get:JsonProperty
    var logging = ""

    @get:JsonProperty
    var pushes = ""

}

class ChannelStorage {
    @get:JsonProperty
    var introId = ""

    @get:JsonProperty
    var users : MutableSet<String> = HashSet()

    @get:JsonProperty
    var admins : MutableSet<String> = HashSet()
}