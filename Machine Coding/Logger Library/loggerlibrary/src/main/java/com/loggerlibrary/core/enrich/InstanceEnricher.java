package com.loggerlibrary.core.enrich;

import com.loggerlibrary.model.Message;

public class InstanceEnricher implements Enricher {
    @Override
    public String apply(String formatted, Message message) {
        return message.getInstanceId() != null ? formatted + " Instance:" + message.getInstanceId() : formatted;
    }
}


