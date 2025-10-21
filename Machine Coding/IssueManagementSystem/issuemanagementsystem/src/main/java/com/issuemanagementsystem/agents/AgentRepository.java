package com.issuemanagementsystem.agents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import com.issuemanagementsystem.interfaces.IAgentRepository;
import com.issuemanagementsystem.users.CustomerAgent;

public class AgentRepository implements IAgentRepository {
    private final Map<String, CustomerAgent> customerAgentMap;
    private final Map<String, List<String>> customerAgentIssueHistory;
    private final Map<String, ConcurrentLinkedQueue<String>> customerAgentIssueQueue;

    @Override
    public void addAgent(CustomerAgent customerAgent) {
        customerAgentMap.put(customerAgent.getId(), customerAgent);
    }

    @Override
    public CustomerAgent getAgentById(String customerAgentId) {
        return customerAgentMap.get(customerAgentId);
    }

    @Override
    public List<CustomerAgent> getAllCustomerAgents() {
        return customerAgentMap.values().stream().collect(Collectors.toList());
    }

    @Override
    public List<String> getAgentWorkHistory(String customerAgentId) {
        return customerAgentIssueHistory.getOrDefault(customerAgentId, new ArrayList<>());
    }

    @Override
    public void assignIssue(String customerAgentId, String issueId) {
        customerAgentIssueQueue.computeIfAbsent(customerAgentId, queue -> new ConcurrentLinkedQueue<>()).add(issueId);
        customerAgentIssueHistory.computeIfAbsent(customerAgentId, list -> new ArrayList<>()).add(issueId);
    }

    @Override
    public String getNextIssue(String customerAgentId) {
        return customerAgentIssueQueue.get(customerAgentId).poll();
    }

    @Override
    public ConcurrentLinkedQueue<String> getIssueQueueById(String customerAgentId) {
        return customerAgentIssueQueue.getOrDefault(customerAgentId, new ConcurrentLinkedQueue<>());
    }

    public AgentRepository() {
        this.customerAgentMap = new ConcurrentHashMap<>();
        this.customerAgentIssueHistory = new ConcurrentHashMap<>();
        this.customerAgentIssueQueue = new HashMap<>();
    }
}