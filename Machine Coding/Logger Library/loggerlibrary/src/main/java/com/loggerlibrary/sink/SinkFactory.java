package com.loggerlibrary.sink;

import com.loggerlibrary.LoggerConfig;

public class SinkFactory {
    public static AbstractSink getSink(LoggerConfig config) {
        String sinkType = config.getSinkType();

        switch (sinkType.toUpperCase()) {
            case "FILE":
                return new FileSink(config.getConfigurations());
            case "CONSOLE":
                return new ConsoleSink(config.getConfigurations());
            default:
                throw new IllegalArgumentException("Unknown sink_type: " + sinkType);
        }
    }
}
