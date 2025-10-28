package com.loggerlibrary.model;

import lombok.Getter;

@Getter
public enum LogLevel {
    DEBUG(1), 
    INFO(2), 
    WARN(3), 
    ERROR(4), 
    FATAL(5);

    private final Integer level;

    LogLevel(Integer level) {
        this.level = level;
    }
}
