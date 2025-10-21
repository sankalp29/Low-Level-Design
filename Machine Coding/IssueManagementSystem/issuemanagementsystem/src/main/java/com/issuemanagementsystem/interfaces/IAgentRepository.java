package com.issuemanagementsystem.interfaces;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import com.issuemanagementsystem.users.CustomerAgent;

public interface IAgentRepository {

    void addAgent(CustomerAgent customerAgent);

    CustomerAgent getAgentById(String customerAgentId);

    List<CustomerAgent> getAllCustomerAgents();

    List<String> getAgentWorkHistory(String customerAgentId);

    void assignIssue(String customerAgentId, String issueId);

    String getNextIssue(String customerAgentId);

    ConcurrentLinkedQueue<String> getIssueQueueById(String customerAgentId);

}