package com.loggerlibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.loggerlibrary.sink.FileSink;
import com.loggerlibrary.sink.TestSink;

import java.io.File;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class LoggerLibraryServiceConcurrencyTest {

    @AfterEach
    void tearDown() {
        TestUtils.resetLoggerServiceSingleton();
    }

    @Test
    void singleton_is_threadsafe_under_race() throws Exception {
        // TestUtils.resetLoggerServiceSingleton();

        int threads = 20;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);
        Set<LoggerLibraryService> instances = new HashSet<>();

        for (int i = 0; i < threads; i++) {
            pool.submit(() -> {
                try {
                    start.await();
                } catch (InterruptedException ignored) {}
                instances.add(LoggerLibraryService.getInstance());
            });
        }

        start.countDown();
        pool.shutdown();
        pool.awaitTermination(5, TimeUnit.SECONDS);

        assertEquals(1, instances.size());
    }

    @Test
    void concurrent_logging_is_safe_and_captures_all_entries() throws Exception {
        // TestUtils.resetLoggerServiceSingleton();
        LoggerLibraryService service = LoggerLibraryService.getInstance();
        TestSink sink = TestSink.create("DEBUG");
        service.registerSink(sink);

        int threads = 10;
        int perThread = 50;
        ExecutorService pool = Executors.newFixedThreadPool(threads);
        CountDownLatch start = new CountDownLatch(1);

        for (int t = 0; t < threads; t++) {
            final int idx = t;
            pool.submit(() -> {
                try { start.await(); } catch (InterruptedException ignored) {}
                for (int i = 0; i < perThread; i++) {
                    Message m = new Message.Builder()
                            .content("m-" + idx + "-" + i)
                            .logLevel(LogLevel.DEBUG)
                            .namespace("ns")
                            .build();
                    service.logMessage(m);
                }
            });
        }

        start.countDown();
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(threads * perThread, sink.getEntries().size());
    }

    @Test
    void testFileRotationCompression() {
        LoggerConfig loggerConfig = new LoggerConfig.LoggerConfigBuilder()
                                    .addConfiguration("ts_format", "dd:mm:yyyy")
                                    .addConfiguration("log_level", "INFO")
                                    .addConfiguration("sink_type", "FILE")
                                    .addConfiguration("max_file_size", "1024")
                                    .addConfiguration("thread_model", "MULTI")
                                    .addConfiguration("write_mode", "ASYNC")
                                    .addConfiguration("max_backup_files", "3")
                                    .addConfiguration("file_location", Paths.get("logs", "test_rotation.log").toString())
                                    .build();
        FileSink fileSink = new FileSink(loggerConfig.getConfigurations());
        LoggerLibraryService loggerLibraryService = LoggerLibraryService.getInstance();
        loggerLibraryService.registerSink(fileSink);

        Message message = new Message.Builder()
                            .content("Info message 1")
                            .logLevel(LogLevel.INFO)
                            .namespace("Appl 1")
                            .build();
        loggerLibraryService.logMessage(message);

        int numThreads = 5;
        int messagesPerThread = 50;
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        CountDownLatch latch = new CountDownLatch(numThreads);
        
        for (int t = 0; t < numThreads; t++) {
            final int threadId = t;
            executor.submit(() -> {
                try {
                    for (int i = 0; i < messagesPerThread; i++) {
                        Message msg = new Message.Builder()
                            .content("This is a longer message to quickly fill up the log file " + i)
                            .logLevel(LogLevel.INFO)
                            .namespace("thread-" + threadId)
                            .build();
                            loggerLibraryService.logMessage(msg);
                        
                        // Small delay to simulate real-world scenario
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
        
        // Verify log files exist (main + backups)
        File mainLog = new File("./logs/test_rotation.log");
        assertTrue(mainLog.exists(), "Main log file should exist");
        
        // Check for at least one backup (rotation happened)
        File backup1 = new File("./logs/test_rotation.log.1.gz");
        assertTrue(backup1.exists(), "At least one backup should exist (rotation occurred)");
    }
}