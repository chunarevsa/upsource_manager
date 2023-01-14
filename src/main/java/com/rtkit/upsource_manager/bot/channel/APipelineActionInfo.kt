package com.rtkit.upsource_manager.bot.channel

import com.rtkit.upsource_manager.IRequest
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.entities.TextChannel
import com.rtkit.upsource_manager.bot.BotInstance
import com.rtkit.upsource_manager.bot.Config
import com.rtkit.upsource_manager.bot.enums.EPlatformBuildActionType

/** @author karpov-em on 06.07.2022*/
abstract class APipelineActionInfo<in RQST : IRequest>(val platform: String) {
    /** Тип события */
    var type = EPlatformBuildActionType.None
        protected set

    /** Время в timestamp, когда событие "протухнет". Менеджер по этому времени отслеживает таймауты и необходимость удаления сообщения. */
    var timeoutStamp = 0L
        protected set

    /** Полный текст сообщения канала, уже содержащий всю необходимую информацию для изменения или создания сообщения */
    var uncommitedMessage: Message = MessageBuilder().append("---").build()
        private set

    /** Информация о пользователе - именно, что должно быть нарисовано, гит юзер, тэг или дискордное упоминание */
    var userId = ""
        protected set

    /** Ссылка на пайплайн */
    protected var titleUrl = ""

    /** Ссылка на коммит, который запустил сборку */
    protected var commitUrl = "---"

    /** Сообщение важное, должны упомянуться маинтейнеры, если настроены */
    private var isEmergency = false

    var channel: TextChannel? = null
    var message: Message? = null


    /** Список пользователей, которые должны быть упомянуты в событии */
    val listenerMentionables = HashSet<String>()

    abstract suspend fun applyAction(request: RQST) : APipelineActionInfo<RQST>

    suspend fun applyTimeoutState(emergency: Boolean): APipelineActionInfo<RQST> {
        type = EPlatformBuildActionType.TimedOut
        timeoutStamp = 0
        isEmergency = emergency
        updateUncommitedMessage()
        return this
    }

    suspend fun applyPreviousActionInfo(prev: APipelineActionInfo<IRequest>): APipelineActionInfo<RQST> {
        this.titleUrl = prev.titleUrl
        if (this.commitUrl == "---") {
            this.commitUrl = prev.commitUrl
        }
        updateUncommitedMessage()
        return this
    }

    protected suspend fun updateUncommitedMessage() {
        val builder = MessageBuilder()

        if (listenerMentionables.isNotEmpty()) {
            builder.append(listenerMentionables.map { BotInstance.getUserMention(it) }.toSet().joinToString(" "))
            builder.append(", площадка __")
            builder.append(platform)
            builder.append("__ ")
            builder.append(type.partMessage)
        }
        if (type == EPlatformBuildActionType.TimedOut && isEmergency) {
            if (listenerMentionables.isNotEmpty()) {
                builder.append("\n")
            }
            builder.append(Config.maintainerRoles.map { BotInstance.getRoleMention(it) }.toSet().joinToString(" "))
        }

        // EmbedMessage explain: https://raw.githubusercontent.com/DV8FromTheWorld/JDA/assets/assets/docs/embeds/07-addField.png
        val embedBuilder = EmbedBuilder()
        embedBuilder.setAuthor(platform, "https://${platform}/", type.reaction.url)

        embedBuilder.setTitle("Площадка ${type.partMessage}", getEmbedMessageUrl())

        embedBuilder.addField(MessageEmbed.Field("Начал", BotInstance.getUserMention(userId), true, true))
        val commitText = if (commitUrl == "---") commitUrl else "[${commitUrl.substring(commitUrl.lastIndexOf("/") + 1)}]($commitUrl)"
        embedBuilder.addField(MessageEmbed.Field("Commit", commitText, true, true))

        embedBuilder.setColor(type.color)

        builder.setEmbeds(embedBuilder.build())
        uncommitedMessage = builder.build()
    }

    protected fun setupTimeouts() {
        val timeout = type.timeout ?: 0
        timeoutStamp = (if (timeout > 0) System.currentTimeMillis() + timeout else 0)
    }

    private fun getEmbedMessageUrl() : String {
        val sonarReference = "https://sonarqube.ritperm.rt.ru/project/issues?resolved=false&inNewCodePeriod=true&id=elk_backend"
        return if (type == EPlatformBuildActionType.SonarFailed) sonarReference else titleUrl
    }
}