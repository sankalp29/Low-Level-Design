package com.logstore;

import java.util.List;

public interface ILogStore {
    public boolean log(Integer timestamp, String message);
    public List<String> getLogs(Integer startTime, Integer endTime);
}
