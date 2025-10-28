package com.loggerlibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.loggerlibrary.core.LoggerConfig;
import com.loggerlibrary.model.LogLevel;

public class LoggerConfigTest {

    @Test
    void build_without_required_fields_throws() {
        LoggerConfig.LoggerConfigBuilder builder = new LoggerConfig.LoggerConfigBuilder();
        assertThrows(IllegalArgumentException.class, builder::build);
    }

    @Test
    void build_with_required_fields_sets_defaults() {
        LoggerConfig config = new LoggerConfig.LoggerConfigBuilder()
                .addConfiguration("sink_type", "CONSOLE")
                .addConfiguration("log_level", "INFO")
                .build();

        assertEquals("CONSOLE", config.getSinkType());
        assertEquals(LogLevel.INFO, config.getLogLevel());
        assertEquals("ddmmyyyyhhmmss", config.getTimestampFormat());
    }
}


