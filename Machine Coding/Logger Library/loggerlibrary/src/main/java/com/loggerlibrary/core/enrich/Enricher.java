package com.loggerlibrary.core.enrich;

import com.loggerlibrary.model.Message;

public interface Enricher {
    String apply(String formatted, Message message);
}


