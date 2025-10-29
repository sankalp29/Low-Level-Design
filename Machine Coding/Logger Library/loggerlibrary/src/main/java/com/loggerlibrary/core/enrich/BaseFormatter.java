package com.loggerlibrary.core.enrich;

import com.loggerlibrary.model.Message;

public class BaseFormatter implements MessageFormatter {
    @Override
    public String format(Message message, String timestamp) {
        StringBuilder sb = new StringBuilder();
        sb.append(message.getLogLevel())
          .append(" [").append(timestamp).append("] ")
          .append(message.getContent())
          .append(" ").append(message.getNamespace())
          .append(" ID:").append(message.getMessageId());
        return sb.toString();
    }
}


