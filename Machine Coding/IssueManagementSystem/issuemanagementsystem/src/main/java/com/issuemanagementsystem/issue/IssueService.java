package com.issuemanagementsystem.issue;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.issuemanagementsystem.exceptions.IssueException;
import com.issuemanagementsystem.exceptions.UserException;
import com.issuemanagementsystem.interfaces.IAgentService;
import com.issuemanagementsystem.interfaces.IIssueRepository;
import com.issuemanagementsystem.interfaces.IIssueService;
import com.issuemanagementsystem.issue.search.IssueSearchQuery;
import com.issuemanagementsystem.strategy.IIssueAssigningStrategy;
import com.issuemanagementsystem.users.Admin;
import com.issuemanagementsystem.users.Customer;

public class IssueService implements IIssueService {
    private final IIssueRepository issueRepository;
    private final IIssueAssigningStrategy issueAssigningStrategy;
    private final IAgentService agentService;

    @Override
    public String createIssue(String transactionId, IssueType issueType, Customer raisedBy, String subject, String description) throws IssueException, UserException {
        if (issueType == null) throw new IssueException("Invalid issue. You need to specify a type");
        if (raisedBy == null) throw new IssueException("Invalid customer passed. Issue needs a valid customer owner.");
        if (transactionId == null || transactionId.isEmpty()) throw new IssueException("Invalid transactionId. Please specify a valid transactionId to create an issue");

        Issue issue = new Issue(transactionId, issueType, raisedBy, subject, description);
        issueRepository.createIssue(issue);
        assignIssue(issue);
        return issue.getIssueId();
    }

    @Override
    public void assignIssue(Issue issue) throws IssueException, UserException {
        issueAssigningStrategy.assignIssueTo(issue);
    }

    @Override
    public void updateIssue(String issueId, IssueStatus issueStatus, String updateNotes) throws IssueException {
        if (issueId == null || issueId.isEmpty()) throw new IssueException("Invalid issueId specified");
        Issue issue = issueRepository.getIssueById(issueId);
        if (issue.getIssueStatus() == issueStatus) throw new IssueException("Assign a new status. New status should be different from current status");
        Customer issueOwner = issue.getRaisedBy();
        issueOwner.notify(issueStatus, updateNotes);
        issue.addToLog(updateNotes);
    }

    @Override
    public List<Issue> getIssues(IssueSearchQuery issueSearchQuery) {
        List<Issue> issues = issueRepository.getAllIssues();
        return issues.stream().filter(issueSearchQuery::matches).collect(Collectors.toList());
    }
    
    @Override
    public void resolveIssue(String issueId, String resolutionNotes) throws IssueException, UserException {
        if (issueId == null || issueId.isEmpty()) throw new IssueException("Invalid issueId specified");
        Issue issue = issueRepository.getIssueById(issueId);
        if (issue.getIssueStatus() == IssueStatus.RESOLVED) throw new IssueException("Issue is already Resolved.");
        if (issue.getIssueStatus() == IssueStatus.ASSIGNED) throw new IssueException("The issue is not yet under work");
        if (issue.getIssueStatus() == IssueStatus.CANCELLED) throw new IssueException("The issue is cancelled. Please open a new issue");

        Customer issueOwner = issue.getRaisedBy();
        issueOwner.notify(IssueStatus.RESOLVED, resolutionNotes);
        issue.addToLog(resolutionNotes);
        
        String nextIssueId = agentService.startNextIssue(issue.getAssignedTo().getId());
        Issue nextIssue = issueRepository.getIssueById(nextIssueId);
        nextIssue.setIssueStatus(IssueStatus.RESOLUTION_IN_PROGRESS);
    }

    @Override
    public List<Issue> viewAgentsWorkHistory(Admin admin, String customerAgentId) throws UserException {
        if (admin == null) throw new UserException("Invalid Admin passed. Admin cannot be null");
        List<String> issueIds = agentService.getAgentWorkHistory(customerAgentId);
        List<Issue> workHistory = new ArrayList<>();
        for (String issueId : issueIds) {
            workHistory.add(issueRepository.getIssueById(issueId));
        }
        return workHistory;
    }

    public IssueService(IIssueRepository issueRepository, IIssueAssigningStrategy issueAssigningStrategy, IAgentService agentService) {
        this.issueRepository = issueRepository;
        this.issueAssigningStrategy = issueAssigningStrategy;
        this.agentService = agentService;
    }
}