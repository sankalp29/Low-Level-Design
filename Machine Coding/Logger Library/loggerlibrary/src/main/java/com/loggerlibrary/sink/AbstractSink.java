package com.loggerlibrary.sink;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class AbstractSink {
    private final Map<String, String> sinkConfiguration;

    public abstract void log(String logEntry);

    public String getConfiguration(String key) {
        return sinkConfiguration.get(key);
    }

    public String getTimestampFormat() {
        return sinkConfiguration.get("ts_format");
    }

    public String getLogLevel() {
        return sinkConfiguration.get("log_level");
    }

    public String getSinkType() {
        return sinkConfiguration.get("sink_type");
    }
}
