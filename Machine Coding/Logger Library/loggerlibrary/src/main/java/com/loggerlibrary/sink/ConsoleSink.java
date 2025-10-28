package com.loggerlibrary.sink;

import java.util.Map;

public class ConsoleSink extends AbstractSink {

    public ConsoleSink(Map<String, String> sinkConfiguration) {
        super(sinkConfiguration);
    }

    @Override
    public void log(String logEntry) {
        System.out.println(logEntry);
    }
    
}
