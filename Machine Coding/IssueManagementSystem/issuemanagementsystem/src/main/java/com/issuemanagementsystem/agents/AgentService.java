package com.issuemanagementsystem.agents;

import java.util.List;

import com.issuemanagementsystem.exceptions.UserException;
import com.issuemanagementsystem.interfaces.IAgentRepository;
import com.issuemanagementsystem.interfaces.IAgentService;
import com.issuemanagementsystem.issue.IssueType;
import com.issuemanagementsystem.users.Admin;
import com.issuemanagementsystem.users.CustomerAgent;

public class AgentService implements IAgentService {
    private IAgentRepository agentRepository;

    @Override
    public String addAgent(Admin admin, String name, String email, List<IssueType> issueTypesHandler) throws UserException {
        if (admin == null) throw new UserException("Invalid admin. Only admin can add a new agent");
        CustomerAgent agent = new CustomerAgent(name, email, issueTypesHandler);
        agentRepository.addAgent(agent);

        return agent.getId();
    }

    @Override
    public CustomerAgent getAgentById(String customerAgentId) throws UserException {
        if (customerAgentId == null || customerAgentId.isEmpty()) throw new UserException("Invalid customer agent id. It cannot be null or empty");
        return agentRepository.getAgentById(customerAgentId);
    }

    @Override
    public void assignIssue(String customerAgentId, String issueId) throws UserException {
        getAgentById(customerAgentId);
        agentRepository.assignIssue(customerAgentId, issueId);
    }

    @Override
    public String startNextIssue(String customerAgentId) {
        return agentRepository.getNextIssue(customerAgentId);
    }

    @Override
    public List<String> getAgentWorkHistory(String customerAgentId) throws UserException {
        getAgentById(customerAgentId);
        return agentRepository.getAgentWorkHistory(customerAgentId);
    }
}
