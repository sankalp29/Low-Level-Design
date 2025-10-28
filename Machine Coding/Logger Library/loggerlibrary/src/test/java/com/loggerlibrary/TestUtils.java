package com.loggerlibrary;

import java.lang.reflect.Field;

final class TestUtils {
    private TestUtils() {}

    static void resetLoggerServiceSingleton() {
        try {
            Field instanceField = LoggerLibraryService.class.getDeclaredField("instance");
            instanceField.setAccessible(true);
            instanceField.set(null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}