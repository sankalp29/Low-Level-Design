package com.issuemanagementsystem.issue.search;

import java.time.LocalDateTime;

import com.issuemanagementsystem.issue.Issue;

public class DateRangeFilter implements IssueSearchFilter {
    private final LocalDateTime startTime, endTime;

    @Override
    public boolean matches(Issue issue) {
        return issue.getRaisedAt().isAfter(startTime) && issue.getRaisedAt().isBefore(endTime);
    }

    public DateRangeFilter(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
