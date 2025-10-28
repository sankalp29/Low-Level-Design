package com.loggerlibrary.sink.console;

import java.util.Map;

import com.loggerlibrary.sink.AbstractSink;

public class ConsoleSink extends AbstractSink {

    public ConsoleSink(Map<String, String> sinkConfiguration) {
        super(sinkConfiguration);
    }

    @Override
    public void log(String logEntry) {
        System.out.println(logEntry);
    }
    
}
