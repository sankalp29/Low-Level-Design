package com.issuemanagementsystem;

import java.util.List;

import com.issuemanagementsystem.agents.AgentRepository;
import com.issuemanagementsystem.agents.AgentService;
import com.issuemanagementsystem.exceptions.IssueException;
import com.issuemanagementsystem.exceptions.UserException;
import com.issuemanagementsystem.interfaces.IAgentRepository;
import com.issuemanagementsystem.interfaces.IAgentService;
import com.issuemanagementsystem.interfaces.IIssueRepository;
import com.issuemanagementsystem.interfaces.IIssueService;
import com.issuemanagementsystem.issue.IssueRepository;
import com.issuemanagementsystem.issue.IssueService;
import com.issuemanagementsystem.issue.IssueType;
import com.issuemanagementsystem.strategy.IIssueAssigningStrategy;
import com.issuemanagementsystem.strategy.WeightedAssigningStrategy;
import com.issuemanagementsystem.users.Admin;
import com.issuemanagementsystem.users.Customer;

public class Main {
    public static void main(String[] args) {
        testHappyFlow();
    }

    private static void testHappyFlow() {
        IIssueRepository issueRepository = new IssueRepository();
        IAgentRepository agentRepository = new AgentRepository();
        IIssueAssigningStrategy strategy = new WeightedAssigningStrategy(agentRepository);
        IAgentService agentService = new AgentService();
        IIssueService issueService = new IssueService(issueRepository, strategy, agentService);
        Customer customer = new Customer("Sankalp", "sankalp@email.com");
        Admin admin = new Admin("Admin", "admin@email.com");
        try {
            issueService.createIssue("TXN-1", IssueType.PAYMENT, customer, "Payment issue", "Desc of payment issue");
            agentService.addAgent(admin, "Agent1", "agent1@email.com", List.of(IssueType.PAYMENT, IssueType.GOLD));

        } catch (IssueException | UserException e) {
            e.printStackTrace();
        }
        
    }
}