package com.loggerlibrary.sink.file.rotation;

import java.io.File;
import java.time.ZonedDateTime;

import lombok.Getter;

@Getter
public final class RotationContext {
    private final File file;
    private final long currentSizeBytes;
    private final int nextEntryBytes;
    private final ZonedDateTime now;

    public RotationContext(File file, long currentSizeBytes, int nextEntryBytes, ZonedDateTime now) {
        this.file = file;
        this.currentSizeBytes = currentSizeBytes;
        this.nextEntryBytes = nextEntryBytes;
        this.now = now;
    }

    public static RotationContext of(String fileLocation, long currentSizeBytes, int nextEntryBytes) {
        return new RotationContext(new File(fileLocation), currentSizeBytes, nextEntryBytes, ZonedDateTime.now());
    }
}


