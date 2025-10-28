package com.loggerlibrary.sink;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class FileSink extends AbstractSink {

    private final String fileLocation;
    private final AtomicLong currentFileSize = new AtomicLong(0);
    private final LogRotator rotator;
    private BufferedWriter writer;

    public FileSink(Map<String, String> sinkConfiguration) {
        super(sinkConfiguration);

        if (sinkConfiguration == null || sinkConfiguration.isEmpty())
            throw new IllegalArgumentException("Sink Configuration cannot be null / empty");
        if (!sinkConfiguration.containsKey("file_location"))
            throw new IllegalArgumentException("File location is required in configuration.");

        this.fileLocation = sinkConfiguration.get("file_location");
        int maxBackupFiles = sinkConfiguration.containsKey("max_backup_files") 
                ? Integer.parseInt(sinkConfiguration.get("max_backup_files")) 
                : 5;
        long maxFileSize = sinkConfiguration.containsKey("max_file_size") 
                ? Long.parseLong(sinkConfiguration.get("max_file_size")) 
                : 10 * 1024 * 1024L;

        this.rotator = new LogRotator(fileLocation, maxFileSize, maxBackupFiles);

        File file = new File(fileLocation);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        
        currentFileSize.set(rotator.getCurrentFileSize());
        initWriter();
    }

    private void initWriter() {
        try {
            this.writer = new BufferedWriter(
                new FileWriter(fileLocation, true),
                8192 // 8KB buffer
            );
        } catch (IOException e) {
            throw new IllegalStateException("Failed to initialize FileSink: " + e.getMessage(), e);
        }
    }

    @Override
    public synchronized void log(String logEntry) {
        int entrySize = logEntry.getBytes(StandardCharsets.UTF_8).length;

        // Rotate if needed
        if (rotator.shouldRotate(currentFileSize.get(), entrySize)) {
            try {
                writer.close();
                long newSize = rotator.rotate(currentFileSize.get());
                currentFileSize.set(newSize);
                initWriter();
            } catch (Exception e) {
                System.err.println("[FileSink] Rotation failed, continuing with current file: " + e.getMessage());
            }
        }

        // Write log entry
        try {
            writer.write(logEntry);
            writer.flush(); // Ensure durability
            currentFileSize.addAndGet(entrySize);
        } catch (IOException e) {
            System.err.println("[FileSink] CRITICAL - Failed to write log entry: " + e.getMessage());
        }
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.flush();
            writer.close();
        }
    }
}
