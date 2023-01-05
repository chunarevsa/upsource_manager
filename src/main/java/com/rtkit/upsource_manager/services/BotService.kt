package com.rtkit.upsource_manager.services

import com.rtkit.upsource_manager.config.BotConfig
import com.rtkit.upsource_manager.config.EReactionType
import com.rtkit.upsource_manager.events.ReadyEventListener
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Activity
import net.dv8tion.jda.api.utils.Compression
import net.dv8tion.jda.internal.entities.EntityBuilder
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.stereotype.Service

@Service
class BotService(

) {
    private val logger: Logger = LogManager.getLogger(BotService::class.java)

    private val botToken: String = BotConfig.BOT_TOKEN

    /** Хранение идентификаторов служебных сообщений. <ChannelId, <Platform|Tag, MessageId>>  */
    var messageIdStore: MutableMap<String, MutableMap<String, String>> = HashMap()

    //    var channels = ChannelIdStore()
    var Emoji_PC = "\uD83D\uDDA5"
    var introMessage: String = "Привет! Здесь ты можешь подписаться на события сборки нашего Gitlab!\n" +
            "Поставь **реакцию** под __нужной площадкой__ ниже, и когда событие произойдёт - я напишу тебе об этом! \n" +
            "\n" +
            EReactionType.values().joinToString("\n") { "${it.emoji} - **${it.title}**" } +
            "\n----------------------------------"

    /** Хранение маппинга пользователей гита на пользователей дискорда */
    var userMapping: MutableMap<String, MutableSet<String>> = HashMap()

    /** Список ролей, которые должны быть уведомлены при таймаутах или нескольких фейлов подряд */
    var maintainerRoles: MutableSet<String> = HashSet()

    var jdaInstance: JDA? = null
        private set

    var botIsWorks: Boolean = false

    init {
        val builder = JDABuilder.createDefault(botToken)
        builder.setCompression(Compression.ZLIB)
        builder.setActivity(
            EntityBuilder.createActivity(
                "Запуск Диcкорд бота ",
                null,
                Activity.ActivityType.CUSTOM_STATUS
            )
        )
//        builder.addEventListeners(this, botChannelHolderManager, BotSlashCommandsHandler, BotCommandHandler) // TODO: удалить
        builder.addEventListeners(ReadyEventListener())

        logger.info("Connecting to Discord...")
        jdaInstance = builder.build().awaitReady()

//        BotSlashCommandsHandler.rebuildSlashCommands()

        logger.info("Bot started")
        botIsWorks = true
//        ThreadPoolManager.scheduleAtFixedRate(1000, 1000, BotChannelHolderManager::onTick)

    }

    fun onTick() {
        logger.info("One Tick")


    }

    fun stopBot() {
        if (jdaInstance != null) {
            try {
                //log("SERVICE SHUTDOWN") // TODO:
                jdaInstance!!.shutdown()
                botIsWorks = false
            } catch (t: Throwable) {
                logger.error("", t)
            }
        }
    }
}