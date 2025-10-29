package com.loggerlibrary.core;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.loggerlibrary.model.LogLevel;
import com.loggerlibrary.model.Message;
import com.loggerlibrary.sink.AbstractSink;
import com.loggerlibrary.sink.factory.SinkFactory;
import com.loggerlibrary.core.enrich.MessageFormatter;
import com.loggerlibrary.core.enrich.Enricher;
import com.loggerlibrary.core.enrich.BaseFormatter;
import com.loggerlibrary.core.enrich.TrackingIdEnricher;
import com.loggerlibrary.core.enrich.HostEnricher;
import com.loggerlibrary.core.enrich.InstanceEnricher;

public class LoggerLibraryService {
    private static volatile LoggerLibraryService instance;
    private final Map<LogLevel, List<AbstractSink>> levelToSinks;
    private final ExecutorService asyncSingle;
    private final ExecutorService asyncMulti;
    // Enrichment pipeline
    private final MessageFormatter baseFormatter;
    private final List<Enricher> enrichers;

    public void registerSink(AbstractSink sink) {
        if (sink == null) throw new IllegalArgumentException("Sink cannot be null");

        LogLevel sinkLevel = LogLevel.valueOf(sink.getLogLevel());
        for (LogLevel level : LogLevel.values()) {
            // Add this sink to all levels >= sink's log level
            if (level.getLevel() >= sinkLevel.getLevel()) {
                levelToSinks.get(level).add(sink);
            }
        }
    }

    public AbstractSink registerSink(LoggerConfig config) {
        if (config == null) throw new IllegalArgumentException("Config cannot be null");

        AbstractSink sink = SinkFactory.getSink(config); // create sink
        registerSink(sink);
        return sink;
    }

    public void logMessage(Message message) {
        List<AbstractSink> sinks = levelToSinks.get(message.getLogLevel());
        if (sinks.isEmpty()) return;

        for (AbstractSink sink : sinks) {
            // Enrich once outside for both sync/async paths
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern(sink.getTimestampFormat()));
            String formatted = baseFormatter.format(message, timestamp);
            for (Enricher enricher : enrichers) {
                formatted = enricher.apply(formatted, message);
            }
            String enrichedMessage = formatted + "\n";
            
            String writeMode = sink.getConfiguration("write_mode") != null ? sink.getConfiguration("write_mode") : "SYNC";
            String threadModel = sink.getConfiguration("thread_model") != null ? sink.getConfiguration("thread_model") : "SINGLE";

            if ("ASYNC".equalsIgnoreCase(writeMode)) {
                ExecutorService exec = "MULTI".equalsIgnoreCase(threadModel) ? asyncMulti : asyncSingle;
                exec.submit(() -> {
                    try {
                        sink.log(" [Async] " + enrichedMessage);
                    } catch (Exception e) {
                        System.err.println("[LoggerLibraryService] Error writing (async) to sink '" + sink.getSinkType() + "': " + e.getMessage());
                    }
                });
            } else {
                try {
                    sink.log(" [Sync] " + enrichedMessage);
                } catch (Exception e) {
                    System.err.println("[LoggerLibraryService] Error writing to sink '" + sink.getSinkType() + "': " + e.getMessage());
                }
            }
        }
    }

    private LoggerLibraryService() {
        levelToSinks = new EnumMap<>(LogLevel.class);
        for (LogLevel level : LogLevel.values()) {
            levelToSinks.put(level, new CopyOnWriteArrayList<>());
        }
        asyncSingle = Executors.newSingleThreadExecutor(r -> new Thread(r, "logger-async-single"));
        int poolSize = Math.max(2, Runtime.getRuntime().availableProcessors() / 2);
        asyncMulti = Executors.newFixedThreadPool(poolSize, r -> new Thread(r, "logger-async-multi"));
        // Default pipeline
        this.baseFormatter = new BaseFormatter();
        this.enrichers = new ArrayList<>();
        this.enrichers.add(new TrackingIdEnricher());
        this.enrichers.add(new HostEnricher());
        this.enrichers.add(new InstanceEnricher());
    }

    public void shutdown() {
        asyncSingle.shutdown();
        asyncMulti.shutdown();
    }
}
