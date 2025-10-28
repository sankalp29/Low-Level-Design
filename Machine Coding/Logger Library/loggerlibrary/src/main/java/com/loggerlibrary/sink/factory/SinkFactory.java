package com.loggerlibrary.sink.factory;

import com.loggerlibrary.core.LoggerConfig;
import com.loggerlibrary.sink.AbstractSink;
import com.loggerlibrary.sink.console.ConsoleSink;
import com.loggerlibrary.sink.file.FileSink;

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
