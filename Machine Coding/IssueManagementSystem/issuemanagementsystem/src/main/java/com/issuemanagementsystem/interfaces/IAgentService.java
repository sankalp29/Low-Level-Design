package com.issuemanagementsystem.interfaces;

import java.util.List;

import com.issuemanagementsystem.exceptions.UserException;
import com.issuemanagementsystem.issue.IssueType;
import com.issuemanagementsystem.users.Admin;
import com.issuemanagementsystem.users.CustomerAgent;

public interface IAgentService {

    String addAgent(Admin admin, String name, String email, List<IssueType> issueTypesHandler) throws UserException;

    CustomerAgent getAgentById(String customerAgentId) throws UserException;

    void assignIssue(String customerAgentId, String issueId) throws UserException;

    String startNextIssue(String customerAgentId);

    List<String> getAgentWorkHistory(String customerAgentId) throws UserException;

}