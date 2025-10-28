package com.loggerlibrary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.loggerlibrary.core.LoggerLibraryService;
import com.loggerlibrary.model.LogLevel;
import com.loggerlibrary.model.Message;
import com.loggerlibrary.sink.TestSink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoggerLibraryServiceLevelFilterTest {

    private LoggerLibraryService service;
    private TestSink debugSink;
    private TestSink errorSink;

    @BeforeEach
    void setup() {
        TestUtils.resetLoggerServiceSingleton();
        service = LoggerLibraryService.getInstance();
        debugSink = TestSink.create("DEBUG");
        errorSink = TestSink.create("ERROR");
        service.registerSink(debugSink);
        service.registerSink(errorSink);
    }

    @Test
    void only_sinks_meeting_level_receive_logs() {
        Message msgInfo = new Message.Builder()
                .content("hello")
                .logLevel(LogLevel.INFO)
                .namespace("ns")
                .build();

        service.logMessage(msgInfo);

        // DEBUG sink receives INFO; ERROR sink does not
        assertEquals(1, debugSink.getEntries().size());
        assertEquals(0, errorSink.getEntries().size());
    }
}


