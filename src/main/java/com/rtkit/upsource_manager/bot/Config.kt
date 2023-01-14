package com.rtkit.upsource_manager.bot

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.rtkit.upsource_manager.IResponse
import com.rtkit.upsource_manager.bot.enums.EReactionType
import java.io.File

/** @author Johnson on 19.02.2021*/

@JsonIgnoreProperties(ignoreUnknown = true)
object Config : IResponse {
    /**  */
    @get:JsonProperty
    var botToken: String = ""

    /** Хранение идентификаторов служебных сообщений. <ChannelId, <Platform|Tag, MessageId>>  */
    @get:JsonProperty
    var messageIdStore: MutableMap<String, MutableMap<String, String>> = HashMap()

    @get:JsonProperty
    var channels = ChannelIdStore()

    @Deprecated("не место ему тут, надо сделать EmojiConst.kt ?")
    var Emoji_PC = "\uD83D\uDDA5"

    /** Интро сообщение в основном канале */
    var introMessage: String = "Привет! Здесь ты можешь подписаться на события сборки нашего Gitlab!\n" +
            "Поставь **реакцию** под __нужной площадкой__ ниже, и когда событие произойдёт - я напишу тебе об этом! \n" +
            "\n" +
            EReactionType.values().joinToString("\n") { "${it.emoji} - **${it.title}**" } +
            "\n----------------------------------"

    /** Хранение маппинга пользователей гита на пользователей дискорда */
    @get:JsonProperty
    var userMapping: MutableMap<String, MutableSet<String>> = HashMap()

    /** Список ролей, которые должны быть уведомлены при таймаутах или нескольких фейлов подряд */
    @get:JsonProperty
    var maintainerRoles: MutableSet<String> = HashSet()

    private val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
    private fun getConfigFile() =
        File(System.getProperty("user.home") + File.separator, "dartit-discord-bot-config.json")

    fun save() {
        val file = getConfigFile()
        file.mkdirs()
        file.delete()
        mapper.writeValue(file, Config)
//        LOGGER.info("Config saved: ${file.absoluteFile}")
    }

    fun load() {
        val file = getConfigFile()
        if (file.exists()) {
//            LOGGER.info("Reding config file ${file.absoluteFile}")
            mapper.readValue(file, this::class.java)
        }
        save()
    }
}

class ChannelIdStore {
    @get:JsonProperty
    var logging = ""

    @get:JsonProperty
    var pushes = ""

}