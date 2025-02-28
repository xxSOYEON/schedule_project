package com.sykim.schedule.backend.common.config;

import lombok.Getter;
import lombok.Setter;

@Getter	@Setter
public class HikariSetting {
    private int connectionTimeout;
    private int maximumPoolSize;
    private int minimumIdle;
    private int idleTimeout;
    private int maxLifetime;
    private String poolName;
}
