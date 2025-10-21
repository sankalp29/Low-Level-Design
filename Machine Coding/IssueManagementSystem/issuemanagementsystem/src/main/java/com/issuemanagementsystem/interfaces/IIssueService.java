package com.issuemanagementsystem.interfaces;

import java.util.List;

import com.issuemanagementsystem.exceptions.IssueException;
import com.issuemanagementsystem.exceptions.UserException;
import com.issuemanagementsystem.issue.Issue;
import com.issuemanagementsystem.issue.IssueStatus;
import com.issuemanagementsystem.issue.IssueType;
import com.issuemanagementsystem.issue.search.IssueSearchQuery;
import com.issuemanagementsystem.users.Admin;
import com.issuemanagementsystem.users.Customer;

public interface IIssueService {

    String createIssue(String transactionId, IssueType issueType, Customer raisedBy, String subject, String description)
            throws IssueException, UserException;

    void assignIssue(Issue issue) throws IssueException, UserException;

    void updateIssue(String issueId, IssueStatus issueStatus, String updateNotes) throws IssueException;

    List<Issue> getIssues(IssueSearchQuery issueSearchQuery);

    void resolveIssue(String issueId, String resolutionNotes) throws IssueException, UserException;

    List<Issue> viewAgentsWorkHistory(Admin admin, String customerAgentId) throws UserException;

}