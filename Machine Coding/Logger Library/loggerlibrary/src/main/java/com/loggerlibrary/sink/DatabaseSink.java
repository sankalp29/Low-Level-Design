package com.loggerlibrary.sink;

import java.util.Map;

import lombok.Getter;

@Getter
public class DatabaseSink extends AbstractSink {

    private final String dbHost;
    private final String dbPort;
    private final String dbUsername;
    private final String dbPassword;
    
    public DatabaseSink(Map<String, String> config) {
        super(config);

        this.dbHost = config.get("db_host");
        this.dbPort = config.get("db_port");
        this.dbUsername = config.get("db_username");
        this.dbPassword = config.get("db_password");

        initConnection();
    }

    private void initConnection() {
        System.out.println("Establishing connection with database...");
    }

    @Override
    public void log(String logEntry) {
        // Dummy implementation
        System.out.println("DB Sink [" + dbHost + ":" + dbPort + "] -> " + logEntry);
    }
    
}
