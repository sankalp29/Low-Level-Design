package com.issuemanagementsystem.issue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.issuemanagementsystem.users.Customer;
import com.issuemanagementsystem.users.CustomerAgent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Issue {
    private final String issueId;
    private final String transactionId;
    private final Customer raisedBy;
    private final String subject;
    private final String description;
    private final IssueType issueType;
    private CustomerAgent assignedTo;
    private IssueStatus issueStatus;
    private final LocalDateTime raisedAt;
    private LocalDateTime lastUpdatedAt;
    private List<String> log;

    public Issue(String transactionId, IssueType issueType, Customer raisedBy, String subject, String description) {
        this.issueId = UUID.randomUUID().toString();
        this.transactionId = transactionId;
        this.issueType = issueType;
        this.raisedBy = raisedBy;
        this.subject = subject;
        this.description = description;
        this.assignedTo = null;
        this.issueStatus = IssueStatus.CREATED;
        this.raisedAt = LocalDateTime.now();
        this.lastUpdatedAt = LocalDateTime.now();
    }

    public void addToLog(String notes) {
        log.add(notes);
    }

    public void assignCustomerAgent(CustomerAgent customerAgent) {
        this.assignedTo = customerAgent;
        this.issueStatus = IssueStatus.ASSIGNED;
    }
}