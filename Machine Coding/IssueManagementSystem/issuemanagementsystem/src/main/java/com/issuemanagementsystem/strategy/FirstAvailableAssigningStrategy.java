package com.issuemanagementsystem.strategy;

import java.util.List;

import com.issuemanagementsystem.interfaces.IAgentRepository;
import com.issuemanagementsystem.issue.Issue;
import com.issuemanagementsystem.users.CustomerAgent;

public class FirstAvailableAssigningStrategy implements IIssueAssigningStrategy {
    private final IAgentRepository agentRepository;

    @Override
    public void assignIssueTo(Issue issue) {
        List<CustomerAgent> customerAgents = agentRepository.getAllCustomerAgents();
        for (CustomerAgent agent : customerAgents) {
            if (agent.getIssueTypesHandler().contains(issue.getIssueType())) {
                issue.setAssignedTo(agent);
                agentRepository.assignIssue(agent.getId(), issue.getIssueId());
                return;
            }
        }
    }

    public FirstAvailableAssigningStrategy(IAgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }
}
