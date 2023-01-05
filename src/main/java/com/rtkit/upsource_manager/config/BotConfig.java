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


//    private val mapper = ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
//    private fun getConfigFile() = File(System.getProperty("user.home") + File.separator, "dartit-discord-bot-config.json")
//      // TODO: ConfigService ?
//    fun save() {
//        val file = getConfigFile()
//        file.mkdirs()
//        file.delete()
//        mapper.writeValue(file, Config)
//        logger.info("Config saved: ${file.absoluteFile}")
//    }
//
//    fun load() {
//        val file = getConfigFile()
//        if (file.exists()) {
//            LOGGER.info("Reding config file ${file.absoluteFile}")
//            mapper.readValue(file, this::class.java)
//        }
//        save()
//    }

}
