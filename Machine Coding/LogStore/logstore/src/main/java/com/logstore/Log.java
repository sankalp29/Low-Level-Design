package com.logstore;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Log {
    private static final AtomicInteger LOG_ID_GENERATOR = new AtomicInteger(0);
    private final Integer logId;
    private final String message;
    private final Integer timestamp;
    
    public Log(String message, Integer timestamp) {
        this.logId = LOG_ID_GENERATOR.incrementAndGet();
        this.message = message;
        this.timestamp = timestamp;
    }

    // For boundary logs
    public Log(Integer logId, String message, Integer timestamp) {
        this.logId = logId;
        this.message = message;
        this.timestamp = timestamp;
    }
}
