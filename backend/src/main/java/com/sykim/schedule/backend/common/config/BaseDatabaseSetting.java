package com.sykim.schedule.backend.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.sykim.schedule.backend.common.enums.DatabaseDriver;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "spring.datasource")
@Component
@Getter	@Setter
public class BaseDatabaseSetting {

    private String url;
    private String driverClassName;
    private String username;
    private String password;
    private DatabaseDriver type;
    private boolean encryption;
    
    private HikariSetting hikari;
	
	@Configuration
    @Profile("local")
    @PropertySource("classpath:application.properties")
    static class LocalConfig {
    }

    @Configuration
    @Profile("dev")
    @PropertySource("classpath:application-dev.db.properties")
    static class DevConfig {
    }

    @Configuration
    @Profile("qa")
    @PropertySource("classpath:application-qa.db.properties")
    static class QaConfig {
    }

    @Configuration
    @Profile("prod")
    @PropertySource("classpath:application-prod.db.properties")
    static class ProdConfig {
    }
}
