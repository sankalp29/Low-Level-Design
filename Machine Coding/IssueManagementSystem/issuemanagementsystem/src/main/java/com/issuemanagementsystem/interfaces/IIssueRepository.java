package com.issuemanagementsystem.interfaces;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.issuemanagementsystem.issue.Issue;
import com.issuemanagementsystem.issue.IssueType;

public interface IIssueRepository {

    Map<IssueType, ConcurrentLinkedQueue<Issue>> getIssueTypeQueue();

    void createIssue(Issue issue);

    Issue getIssueById(String issueId);

    List<Issue> getAllIssues();

}