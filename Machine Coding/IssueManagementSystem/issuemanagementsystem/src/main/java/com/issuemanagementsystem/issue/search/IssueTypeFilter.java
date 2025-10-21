package com.issuemanagementsystem.issue.search;

import com.issuemanagementsystem.issue.Issue;
import com.issuemanagementsystem.issue.IssueType;

public class IssueTypeFilter implements IssueSearchFilter {
    private final IssueType issueType;
    
    @Override
    public boolean matches(Issue issue) {
        return issueType.equals(issue.getIssueType());
    }

    public IssueTypeFilter(IssueType issueType) {
        this.issueType = issueType;
    }
}
