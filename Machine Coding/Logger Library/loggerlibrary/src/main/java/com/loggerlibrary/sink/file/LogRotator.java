package com.loggerlibrary.sink.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.zip.GZIPOutputStream;

import com.loggerlibrary.sink.file.rotation.RotationContext;
import com.loggerlibrary.sink.file.rotation.RotationStrategy;
import com.loggerlibrary.sink.file.rotation.SizeBasedRotation;

import lombok.Getter;

@Getter
public class LogRotator {
    private final String fileLocation;
    private final int maxBackupFiles;
    private final RotationStrategy rotationStrategy;

    // public LogRotator(String fileLocation, RotationStrategy rotationStrategy, long maxFileSizeBytes, int maxBackupFiles) {
    //     this.fileLocation = fileLocation;
    //     this.maxBackupFiles = maxBackupFiles;
    //     this.rotationStrategy = rotationStrategy;
    // }

    public LogRotator(String fileLocation, RotationStrategy rotationStrategy, int maxBackupFiles) {
        this.fileLocation = fileLocation;
        this.maxBackupFiles = maxBackupFiles;
        this.rotationStrategy = rotationStrategy;
    }

    public boolean shouldRotate(long currentSize, int entrySize) {
        return rotationStrategy.shouldRotate(RotationContext.of(fileLocation, currentSize, entrySize));
    }

    public long rotate(long currentFileSize) {
        File currentFile = new File(fileLocation);

        File oldest = new File(fileLocation + "." + maxBackupFiles + ".gz");
        if (oldest.exists()) oldest.delete();

        for (int i = maxBackupFiles - 1; i >= 1; i--) {
            File older = new File(fileLocation + "." + i + ".gz");
            if (older.exists()) {
                File newer = new File(fileLocation + "." + (i + 1) + ".gz");
                try {
                    Files.move(older.toPath(), newer.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    System.err.println("[LogRotator] Failed to move '" + older.getName() + "' -> '" + newer.getName() + "': " + e.getMessage());
                }
            }
        }

        if (currentFile.exists()) {
            File backup = new File(fileLocation + ".1.gz");
            try {
                compressFile(currentFile, backup);
                if (!currentFile.delete()) {
                    System.err.println("[LogRotator] WARNING: Could not delete " + currentFile.getName());
                }
            } catch (Exception e) {
                System.err.println("[LogRotator] Failed to compress/delete current log: " + e.getMessage());
            }
            return rotationStrategy.sizeAfterRotate();
        }
        return currentFileSize;
    }

    private void compressFile(File source, File destination) {
        try (FileInputStream fis = new FileInputStream(source);
             FileOutputStream fos = new FileOutputStream(destination);
             GZIPOutputStream gzip = new GZIPOutputStream(fos)) {
            
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) != -1) {
                gzip.write(buffer, 0, length);
            }
        } catch (Exception e) {
            System.err.println("[LogRotator] Compression error: " + e.getMessage());
        }
    }

    public long getCurrentFileSize() {
        File file = new File(fileLocation);
        return file.exists() ? file.length() : 0;
    }    
}