package com.loggerlibrary.sink.file.rotation;

import java.util.Map;

public final class RotationStrategyFactory {
    private RotationStrategyFactory() {}

    public static RotationStrategy fromConfig(Map<String, String> cfg) {
        String policy = cfg.getOrDefault("rotation_policy", "SIZE");
        switch (policy.toUpperCase()) {
            case "SIZE": {
                long maxBytes = cfg.containsKey("max_file_size")
                        ? Long.parseLong(cfg.get("max_file_size"))
                        : 10 * 1024 * 1024L;
                return new SizeBasedRotation(maxBytes);
            }
            case "TIME":
                throw new IllegalArgumentException("TIME rotation policy not implemented");
            default:
                throw new IllegalArgumentException("Unsupported rotation_policy: " + policy);
        }
    }
}


