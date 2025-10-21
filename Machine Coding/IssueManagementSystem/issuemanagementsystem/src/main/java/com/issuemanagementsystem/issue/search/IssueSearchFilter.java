package com.issuemanagementsystem.issue.search;

import com.issuemanagementsystem.issue.Issue;

public interface IssueSearchFilter {
    boolean matches(Issue issue);
}
