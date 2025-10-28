package com.loggerlibrary.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public final class Message {
    private final String messageId;
    private final String content;
    private final LogLevel logLevel;
    private final String namespace;

    // Optional fields
    private final String trackingId;
    private final String hostId;
    private final String instanceId;

    private Message(Builder builder) {
        this.messageId = UUID.randomUUID().toString();
        this.content = builder.content;
        this.logLevel = builder.logLevel;
        this.namespace = builder.namespace;
        this.trackingId = builder.trackingId;
        this.hostId = builder.hostId;
        this.instanceId = builder.instanceId;
    }

    public static class Builder {
        private String content;
        private LogLevel logLevel;
        private String namespace;
        private String trackingId;
        private String hostId;
        private String instanceId;

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public Builder logLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        public Builder namespace(String namespace) {
            this.namespace = namespace;
            return this;
        }

        public Builder trackingId(String trackingId) {
            this.trackingId = trackingId;
            return this;
        }

        public Builder hostId(String hostId) {
            this.hostId = hostId;
            return this;
        }

        public Builder instanceId(String instanceId) {
            this.instanceId = instanceId;
            return this;
        }

        public Message build() {
            if (content == null || logLevel == null || namespace == null) {
                throw new IllegalArgumentException("content, logLevel, and namespace are required");
            }
            return new Message(this);
        }
    }
}