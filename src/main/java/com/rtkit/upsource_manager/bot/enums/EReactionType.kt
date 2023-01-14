package com.rtkit.upsource_manager.bot.enums

import net.dv8tion.jda.internal.entities.emoji.UnicodeEmojiImpl

/** @author karpov-em on 08.07.2022*/
enum class EReactionType(val title: String, val emoji: String, val url: String) {
    START("площадка начала сборку", "\u2692", "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/2692.png"),
    SUCCESS("площадка собрана", "\uD83C\uDF1F", "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f31f.png"),
    FAIL("сборка площадки упала", "\uD83D\uDCDB", "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f4db.png");

    fun toEmoji() = UnicodeEmojiImpl(emoji)
}