package com.loggerlibrary.core.enrich;

import com.loggerlibrary.model.Message;

public class TrackingIdEnricher implements Enricher {
    @Override
    public String apply(String formatted, Message message) {
        return message.getTrackingId() != null ? formatted + " TrackingID:" + message.getTrackingId() : formatted;
    }
}


