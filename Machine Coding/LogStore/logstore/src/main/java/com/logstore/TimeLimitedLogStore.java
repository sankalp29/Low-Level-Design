package com.logstore;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TreeSet;

public class TimeLimitedLogStore implements ILogStore {
    private final TreeSet<Log> logs;
    private static TimeLimitedLogStore INSTANCE;
    private final Integer expiryTime;

    /**
     * Time complexity : O(logN)
    */
    @Override
    public boolean log(Integer timestamp, String message) {
        try {
            validateTimestamp(timestamp);
            validateLogMessage(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }

        Log log = new Log(message, timestamp);

        synchronized (this) {
            deleteExpiredLogs();
            logs.add(log);   
        }
        return true;
    }

    /**
     * Time complexity : O(logN + K)
     * K = number of logs with timestamp between [startTime, endTime]
    */
    @Override
    public List<String> getLogs(Integer startTime, Integer endTime) {
        if (!validateTimestamp(startTime) || !validateTimestamp(endTime)) return new ArrayList<>();
        if (endTime < startTime) {
            System.out.println("Invalid time range passed. endTime should be >= startTime");
            return new ArrayList<>();
        }

        Log startLog = new Log(Integer.MIN_VALUE, "", startTime);
        Log endLog = new Log(Integer.MAX_VALUE, "", endTime);
        
        synchronized (this) {
            deleteExpiredLogs();
            return logs.subSet(startLog, true, endLog, true)
                .stream()
                .map(Log::getMessage)
                .toList();    
        }
    }

    /**
     * Time complexity : O(KlogN)
     * K = number of expired entries
     */
    private void deleteExpiredLogs() {
        if (logs.isEmpty()) return;
        Integer endTimestamp = logs.last().getTimestamp() - expiryTime;
        if (endTimestamp < 0) return;
        Log end = new Log("endLog", endTimestamp);
        logs.headSet(end, true).clear();
    }

    private boolean validateTimestamp(Integer timestamp) {
        if (timestamp == null) throw new IllegalArgumentException("Invalid timestamp passed. Timestamp cannot be null");
        if (timestamp <= 0) throw new IllegalArgumentException("Invalid timestamp passed to log(). Timestamp cannot be <= 0");
        return true;
    }

    private boolean validateLogMessage(String message) {
        if (message == null || message.isBlank()) {
            System.err.println("Log message cannot be empty / blank");
            return false;
        }
        return true;
    }

    public static TimeLimitedLogStore getInstance(Integer expiryTime) {
        if (INSTANCE == null) {
            synchronized (TimeLimitedLogStore.class) {
                if (INSTANCE == null) INSTANCE = new TimeLimitedLogStore(expiryTime);       
            }
        }
        return INSTANCE;
    }

    private TimeLimitedLogStore(Integer expiryTime) {
        Comparator<Log> comparator = (log1, log2) -> {
            if (!log1.getTimestamp().equals(log2.getTimestamp())) 
                return Integer.compare(log1.getTimestamp(), log2.getTimestamp());
            return log1.getLogId().compareTo(log2.getLogId());
        };
        this.logs = new TreeSet<>(comparator);
        this.expiryTime = expiryTime;
    }
}
