package com.rtkit.upsource_manager.bot

import net.dv8tion.jda.api.MessageBuilder
import net.dv8tion.jda.api.entities.Message
import java.text.SimpleDateFormat
import java.util.*

/**  Основная логика в том, чтобы хранить информацию о текущем билде по каждой площадке.
 * Если на площадку прилетает событие с отличающимися айдишниками - то это уже новая сборка, и предыдущую нужно оставить в покое.
 * Если же прилетает событие с известными айдишниками - то надо сообщение отредактировать, добавив новую строку лога.
 * @author karpov-em on 08.07.2022*/
object GitlabWebhookLogger {
    private val sdf = SimpleDateFormat("dd.MM.yyyy hh:mm:ss")
//
//    suspend fun logBuild(request: GitlabWebhookBuildRequest) {
//        val userName = BotInstance.getUserMention(request.user.username)
//
//        val content = "`BUILD   ` -> `${request.buildName}`/`${request.buildStage}`/`${request.buildStatus}`"
//        val messageSent = sendLogLine(request.platform, request.buildSha, userName, content)
//        if (!messageSent) {
//            BotInstance.log(
//                "Platform `${request.platform}` performs **BUILD** action (${userName}: `${request.buildName}`/`${request.buildStage}`/`${request.buildStatus}`)\n" +
//                        "`${request.buildSha}`"
//            )
//        }
//    }
//
//    suspend fun logPipeline(request: GitlabWebhookPipelineRequest) {
//        val userName = BotInstance.getUserMention(request.user.username)
//
//        val content = "`PIPELINE` -> `${request.attributes.source}` -> `${request.attributes.status}`"
//        val messageSent = sendLogLine(request.platform, request.buildSha, userName, content)
//        if (!messageSent) {
//            BotInstance.log(
//                "Platform `${request.platform}` performs **PIPELINE** action (${userName}: `${request.attributes.source}` -> `${request.attributes.status}`)\n" +
//                        "`${request.buildSha}`"
//            )
//        }
//    }
//
//    suspend fun logDeployment(request: GitlabWebhookDeploymentRequest) {
//        val userName = BotInstance.getUserMention(request.user.username)
//
//        val content = "`DEPLOY  ` -> `${request.status}`"
//        val messageSent = sendLogLine(request.platform, request.buildSha, userName, content)
//        if (!messageSent) {
//            BotInstance.log(
//                "Platform `${request.platform}` performs **DEPLOYMENT** action (${userName}: `${request.status}`)\n" +
//                        "`${request.buildSha}`"
//            )
//        }
//    }
//
//    suspend fun logPush(request: GitlabWebhookPushRequest) {
//        val userName = BotInstance.getUserMention(request.userUsername)
//        if (Config.channels.pushes.isNotEmpty()) {
//            val channel = BotInstance.getTextChannel(Config.channels.pushes)
//            if (channel != null) {
//                val builder = MessageBuilder()
//                    .append("[`${sdf.format(Date())}`] ")
//                    .append(userName)
//                    .append(" ")
//
//                if (request.checkoutSha.isNullOrEmpty()) {
//                    builder.append("**deleted the branch** __${request.project.name}/${request.platform}__!")
//                } else {
//                    builder.append("pushes ${request.totalCommitsCount} commits to the branch __${request.project.name}/${request.platform}__:\n")
//                    request.commits.forEach { commit ->
//                        builder.append("> +++\t${commit.title} - <${commit.url}> - `${commit.modified.size + commit.removed.size + commit.added.size}` file(s) affected\n")
//                    }
//                }
//
//                channel.sendMessage(builder.denyMentions(Message.MentionType.USER).build()).queue()
//            }
//        }
//
//        sendLogLine(
//            request.platform,
//            request.checkoutSha ?: "",
//            userName,
//            "`PUSH    ` -> ${request.totalCommitsCount} commits\n"
//        )
//    }
//
//    /**  */
//    private suspend fun sendLogLine(platform: String, sha: String, userName: String, line: String): Boolean {
//        if (!Config.messageIdStore.flatMap { it.value.keys }.filter { it != "INTRO" }.toSet()
//                .contains(platform)
//        ) return false
//        if (Config.channels.logging.isEmpty()) return false
//        if (sha.isEmpty()) return false
//
//        try {
//            val channel = BotInstance.getTextChannel(Config.channels.logging)!!
//            val messages = channel.history.retrievePast(30).await()
//            val message = messages.find { message -> message.contentRaw.contains("`${sha}`") }
//            if (message != null) {
//                val newContent = "${message.contentRaw}\n[`${sdf.format(Date())}`] $line\n"
//                message.editMessage(BotInstance.getMessageWithoutMentions(newContent)).await()
//            } else {
//                channel.sendMessage(
//                    MessageBuilder("$userName - `${platform}` - `${sha}`\n")
//                        .append("[`${sdf.format(Date())}`] ")
//                        .append(line)
//                        .denyMentions(Message.MentionType.USER)
//                        .build()
//                ).await()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//            return false
//        }
//
//        return true
//    }
}