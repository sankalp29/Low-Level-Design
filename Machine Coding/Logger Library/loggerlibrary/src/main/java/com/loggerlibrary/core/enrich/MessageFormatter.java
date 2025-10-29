package com.loggerlibrary.core.enrich;

import com.loggerlibrary.model.Message;

public interface MessageFormatter {
    String format(Message message, String timestamp);
}


