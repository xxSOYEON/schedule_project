package com.sykim.schedule.backend.common.enums;

public enum DatabaseDriver {
    H2("org.h2.Driver"),
    MYSQL("com.mysql.cj.jdbc.Driver");

    private final String driverClassName;

    DatabaseDriver(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public static DatabaseDriver from(String driverClassName) {
        if (driverClassName == null) {
            throw new IllegalArgumentException("Driver class name cannot be null");
        }

        String upperDriverClassName = driverClassName.toLowerCase();

        for (DatabaseDriver driver : DatabaseDriver.values()) {
            if (driver.getDriverClassName().toLowerCase().equals(upperDriverClassName)) {
                return driver;
            }
        }
        throw new IllegalArgumentException("Unsupported driver: " + driverClassName);
    }
}