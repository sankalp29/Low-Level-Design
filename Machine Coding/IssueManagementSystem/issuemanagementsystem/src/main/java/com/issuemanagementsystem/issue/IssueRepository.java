package com.issuemanagementsystem.issue;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import com.issuemanagementsystem.interfaces.IIssueRepository;

import lombok.Getter;

public class IssueRepository implements IIssueRepository {
    private final Map<String, Issue> issuesMap;
    private final Map<String, List<String>> customerIssuesMap;
    @Getter
    private final Map<IssueType, ConcurrentLinkedQueue<Issue>> issueTypeQueue;

    @Override
    public void createIssue(Issue issue) {
        String issueId = issue.getIssueId();
        String customerId = issue.getRaisedBy().getId();

        issuesMap.put(issueId, issue);
        issueTypeQueue.computeIfAbsent(issue.getIssueType(), queue -> new ConcurrentLinkedQueue<>()).add(issue);
        customerIssuesMap.computeIfAbsent(customerId, list -> new CopyOnWriteArrayList<>()).add(issueId);
    }

    @Override
    public Issue getIssueById(String issueId) {
        return issuesMap.get(issueId);
    }

    @Override
    public List<Issue> getAllIssues() {
        return issuesMap.values().stream().collect(Collectors.toList());
    }

    public IssueRepository() {
        this.issuesMap = new ConcurrentHashMap<>();
        this.customerIssuesMap = new ConcurrentHashMap<>();
        this.issueTypeQueue = new ConcurrentHashMap<>();
    }
}