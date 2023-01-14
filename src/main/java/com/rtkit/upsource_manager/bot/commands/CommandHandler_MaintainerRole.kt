package com.rtkit.upsource_manager.bot.commands

import com.rtkit.upsource_manager.ReflectiveOperation
import com.rtkit.upsource_manager.bot.BotInstance
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import com.rtkit.upsource_manager.bot.Config

/** @author Johnson on 19.02.2021*/
@ReflectiveOperation
class CommandHandler_MaintainerRole : BotCommandHandler.ICommandHandler() {
    override val command: String = "set-maintainer-roles"
    override val adminOnly: Boolean = true

    override fun onCommand(event: MessageReceivedEvent): Boolean {
        runBlocking {
            launch {
                Config.maintainerRoles.clear()
                Config.maintainerRoles.addAll(event.message.mentions.roles.map { it.id })
                Config.save()

                if (Config.maintainerRoles.isEmpty()) {
                    BotInstance.sendTimedMessage(
                        event.textChannel,
                        "Роли меинтейнеров очищены"
                    )

                } else {
                    BotInstance.sendTimedMessage(
                        event.textChannel,
                        BotInstance.getMessageWithoutMentions(
                            "Роли ${
                                Config.maintainerRoles.joinToString(", ") {
                                    BotInstance.getRoleMention(
                                        it
                                    )
                                }
                            } назначены меинтейнерами"
                        )
                    )
                }
            }
        }

        return true
    }
}