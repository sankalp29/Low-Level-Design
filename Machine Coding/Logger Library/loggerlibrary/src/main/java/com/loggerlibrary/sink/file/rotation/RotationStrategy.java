package com.loggerlibrary.sink.file.rotation;

public interface RotationStrategy {
    boolean shouldRotate(RotationContext context);
    long sizeAfterRotate();
}


