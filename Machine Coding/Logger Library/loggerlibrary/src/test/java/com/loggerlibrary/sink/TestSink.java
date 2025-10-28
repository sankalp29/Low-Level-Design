package com.loggerlibrary.sink;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestSink extends AbstractSink {
    private final List<String> entries = Collections.synchronizedList(new ArrayList<>());

    public TestSink(Map<String, String> sinkConfiguration) {
        super(sinkConfiguration);
    }

    public static TestSink create(String level) {
        Map<String, String> cfg = new HashMap<>();
        cfg.put("sink_type", "TEST");
        cfg.put("log_level", level);
        cfg.put("ts_format", "yyyy-MM-dd HH:mm:ss");
        return new TestSink(cfg);
    }

    @Override
    public void log(String logEntry) {
        entries.add(logEntry);
    }

    public List<String> getEntries() {
        return entries;
    }
}


