package com.loggerlibrary.core.enrich;

import com.loggerlibrary.model.Message;

public class HostEnricher implements Enricher {
    @Override
    public String apply(String formatted, Message message) {
        return message.getHostId() != null ? formatted + " Host:" + message.getHostId() : formatted;
    }
}


