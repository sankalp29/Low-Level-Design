package com.issuemanagementsystem.strategy;

import com.issuemanagementsystem.issue.Issue;

public interface IIssueAssigningStrategy {
    void assignIssueTo(Issue issue);
}
