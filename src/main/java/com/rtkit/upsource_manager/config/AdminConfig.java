package com.rtkit.upsource_manager.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:auth.properties")
public class AdminConfig {
    public static String ADMIN_BASIC_AUTH;

    @Value(value = "${auth.data}")
    public void setAdminBasicAuth(String data) {
        ADMIN_BASIC_AUTH = "Basic " + data;
    }

}
