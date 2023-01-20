package com.rtkit.upsource_manager.bot.enums

import net.dv8tion.jda.internal.entities.emoji.UnicodeEmojiImpl

enum class EEmoji(val emoji: String, val url: String) {
    STARS("\uD83C\uDF1F", "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f31f.png"),
    BLOCK( "\uD83D\uDCDB", "https://raw.githubusercontent.com/twitter/twemoji/master/assets/72x72/1f4db.png");

    fun toEmoji() = UnicodeEmojiImpl(emoji)
}