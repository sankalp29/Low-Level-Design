package com.issuemanagementsystem.issue.search;

import java.util.ArrayList;
import java.util.List;

import com.issuemanagementsystem.issue.Issue;

public class IssueSearchQuery {
    private final List<IssueSearchFilter> filters;

    public void addSearchFilter(IssueSearchFilter filter) {
        filters.add(filter);
    }

    public boolean matches(Issue issue) {
        return filters.stream().allMatch(filter -> filter.matches(issue));
    }

    public IssueSearchQuery() {
        filters = new ArrayList<>();
    }
}
