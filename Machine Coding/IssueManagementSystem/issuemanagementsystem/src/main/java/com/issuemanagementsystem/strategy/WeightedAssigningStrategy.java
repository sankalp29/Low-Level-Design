package com.issuemanagementsystem.strategy;

import java.util.List;

import com.issuemanagementsystem.interfaces.IAgentRepository;
import com.issuemanagementsystem.issue.Issue;
import com.issuemanagementsystem.users.CustomerAgent;

public class WeightedAssigningStrategy implements IIssueAssigningStrategy {
    private final IAgentRepository agentRepository;

    @Override
    public void assignIssueTo(Issue issue) {
        CustomerAgent leastBurdened = null;
        Integer burden = Integer.MAX_VALUE;
        List<CustomerAgent> customerAgents = agentRepository.getAllCustomerAgents();
        for (CustomerAgent agent : customerAgents) {
            if (agent.getIssueTypesHandler().contains(issue.getIssueType())) {
                Integer agentBurden = agentRepository.getIssueQueueById(agent.getId()).size();
                if (agentBurden < burden) {
                    leastBurdened = agent;
                    burden = agentBurden;
                }
            }
        }

        issue.setAssignedTo(leastBurdened);
        agentRepository.assignIssue(leastBurdened.getId(), issue.getIssueId());
    }

    public WeightedAssigningStrategy(IAgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }
}
