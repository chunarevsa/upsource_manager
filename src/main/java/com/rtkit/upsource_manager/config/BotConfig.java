package com.rtkit.upsource_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:discordBot.properties")
public class BotConfig {

    public static String BOT_TOKEN;

    @Value(value = "${bot.token}")
    public void setBotToken(String token) {
        BOT_TOKEN = token;
    }

    public String introMessage = "Привет! Здесь ты можешь подписаться на события сборки нашего Gitlab!\n" +
            "Поставь **реакцию** под __нужной площадкой__ ниже, и когда событие произойдёт - я напишу тебе об этом! \n" +
            "\n" +
            getReactionTypes() +
            "\n----------------------------------";

    public String getReactionTypes() {
        StringBuilder values = new StringBuilder();
        for (EReactionType one : EReactionType.values()) {
            values.append("\n").append(one.getEmoji()).append(one.getTitle());
        }
        return values.toString();
    }

}
