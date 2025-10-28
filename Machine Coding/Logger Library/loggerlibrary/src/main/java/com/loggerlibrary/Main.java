package com.loggerlibrary;

import java.nio.file.Paths;

import com.loggerlibrary.core.LoggerConfig;
import com.loggerlibrary.core.LoggerLibraryService;
import com.loggerlibrary.model.LogLevel;
import com.loggerlibrary.model.Message;
import com.loggerlibrary.sink.db.DatabaseSink;

public class Main {
    public static void main(String[] args) {
        LoggerConfig loggerConfig1 = new LoggerConfig.LoggerConfigBuilder()
                                    .addConfiguration("ts_format", "dd:mm:yyyy")
                                    .addConfiguration("log_level", "INFO")
                                    .addConfiguration("sink_type", "FILE")
                                    .addConfiguration("max_file_size", "1024")
                                    .addConfiguration("thread_model", "MULTI")
                                    .addConfiguration("write_mode", "ASYNC")
                                    .addConfiguration("file_location", Paths.get("logs", "test_rotation.log").toString())
                                    .build();
        
        LoggerConfig loggerConfig2 = new LoggerConfig.LoggerConfigBuilder()
                                    .addConfiguration("ts_format", "dd:mm:yyyy")
                                    .addConfiguration("log_level", "INFO")
                                    .addConfiguration("sink_type", "CONSOLE")
                                    .build();
        
        LoggerLibraryService loggerLibraryService = LoggerLibraryService.getInstance();
        loggerLibraryService.registerSink(loggerConfig1);
        loggerLibraryService.registerSink(loggerConfig2);

        LoggerConfig loggerConfig3 = new LoggerConfig.LoggerConfigBuilder()
                                    .addConfiguration("ts_format", "dd:mm:yyyy")
                                    .addConfiguration("log_level", "WARN")
                                    .addConfiguration("sink_type", "DATABASE")
                                    .build();
        DatabaseSink databaseSink = new DatabaseSink(loggerConfig3.getConfigurations());
        loggerLibraryService.registerSink(databaseSink);

        Message message1 = new Message.Builder()
                            .content("Info message 1")
                            .logLevel(LogLevel.INFO)
                            .namespace("Appl 1")
                            .build();

        Message message2 = new Message.Builder()
                            .content("ERROR message 1")
                            .logLevel(LogLevel.ERROR)
                            .namespace("Appl 2")
                            .build();

        loggerLibraryService.logMessage(message1);
        loggerLibraryService.logMessage(message2);

        loggerLibraryService.shutdown();
    }
}