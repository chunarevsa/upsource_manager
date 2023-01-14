package com.rtkit.upsource_manager.bot.commands

import com.rtkit.upsource_manager.ReflectiveOperation
import com.rtkit.upsource_manager.bot.BotInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.message.MessageReceivedEvent

/** @author Johnson on 19.02.2021*/
@ReflectiveOperation
class CommandHandler_TestMention : BotCommandHandler.ICommandHandler() {
    override val command: String = "mention"

    override fun onCommand(event: MessageReceivedEvent): Boolean {
        val search = event.message.contentRaw.substring(command.length + 2)

        runBlocking {
            launch {
                val userMention = BotInstance.getUserMention(search)
                event.textChannel.sendMessage(BotInstance.getMessageWithoutMentions("Тест поиска пользователя: `$search` -> $userMention"))
                    .queue()
            }
        }

        return true
    }
}