package com.loggerlibrary.sink.file.rotation;

public final class SizeBasedRotation implements RotationStrategy {
    private final long maxBytes;

    public SizeBasedRotation(long maxBytes) {
        this.maxBytes = maxBytes;
    }

    @Override
    public boolean shouldRotate(RotationContext context) {
        return context.getCurrentSizeBytes() + context.getNextEntryBytes() > maxBytes;
    }

    @Override
    public long sizeAfterRotate() {
        return 0L;
    }
}


