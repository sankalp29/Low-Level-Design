package com.loggerlibrary;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

@Getter
public class LoggerConfig {

    private final Map<String, String> configurations;

    // Mandatory fields with type-safe accessors
    public String getSinkType() {
        return configurations.getOrDefault("sink_type", "CONSOLE");
    }

    public LogLevel getLogLevel() {
        return LogLevel.valueOf(configurations.getOrDefault("log_level", "DEBUG"));
    }

    public String getTimestampFormat() {
        return configurations.getOrDefault("ts_format", "ddmmyyyyhhmmss");
    }

    public String getThreadModel() {
        return configurations.getOrDefault("thread_model", "SINGLE");
    }

    public String getWriteMode() {
        return configurations.getOrDefault("write_mode", "SYNC");
    }

    public String getProperty(String key) {
        return configurations.get(key);
    }

    private LoggerConfig(Map<String, String> configurations) {
        this.configurations = new HashMap<>(configurations);
    }

    // Builder
    public static class LoggerConfigBuilder {
        private final Map<String, String> configurations;

        public LoggerConfigBuilder addConfiguration(String key, String value) {
            configurations.put(key, value);
            return this;
        }

        public LoggerConfig build() {
            // Validate mandatory fields
            if (!configurations.containsKey("sink_type")) {
                throw new IllegalArgumentException("sink_type is required");
            }
            if (!configurations.containsKey("log_level")) {
                throw new IllegalArgumentException("log_level is required");
            }
            if (!configurations.containsKey("ts_format")) {
                configurations.put("ts_format", "ddmmyyyyhhmmss");
            }
            return new LoggerConfig(configurations);
        }

        public LoggerConfigBuilder() {
            configurations = new HashMap<>();
        }
    }
}
