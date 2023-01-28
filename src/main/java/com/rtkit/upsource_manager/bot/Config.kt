package com.rtkit.upsource_manager.bot

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.io.File

@JsonIgnoreProperties(ignoreUnknown = true)
object Config : IResponse {
    private val logger: Logger = LogManager.getLogger(Config::class.java)

    /** Токен Discord бота */
    @get:JsonProperty
    var botToken: String = ""

    @get:JsonProperty
    var channels = ChannelIdStore()

    /** Информация о конкретном канале */
    @get:JsonProperty
    var channelStorage: MutableMap<String, ChannelStorage> = HashMap()

    /** Интро сообщение в основном канале */
    var introMessage: String = "Привет! \n" +
            "Здесь ты можешь управлять своими ревью из Upsource!\n"

    /** Хранение маппинга пользователей гита на пользователей дискорда */
    @get:JsonProperty
    var userMap: MutableMap<String, UserMap> = HashMap()

    /** Список логинов в upsource. Заполняется при первом апдейте */
    @JsonIgnore
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

    /** Получение userMap по ид канала.
     * Использовать только при уже инициализированном канале и пользователе */
    fun getUserMapByChannelId(channelId: String): UserMap {
        val discordId = channelStorage[channelId]?.user ?: run {
            throw Exception("Что-то пошло не так. Перед функцией getUserMapByChannelId не выполнены проверки")
        }
        return userMap[discordId]!!
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
    var user: String = ""

    @get:JsonProperty
    var admins: MutableList<String> = mutableListOf()
}

class UserMap {
    @get:JsonProperty
    var discordUsername: String? = null

    @get:JsonProperty
    var discordUserMention: String? = null

    @get:JsonProperty
    var upsourceLogin: String? = null

    @JsonIgnore
    fun getMapping(): MutableList<String?> {
        return mutableListOf(discordUsername, discordUserMention, upsourceLogin)
    }
}