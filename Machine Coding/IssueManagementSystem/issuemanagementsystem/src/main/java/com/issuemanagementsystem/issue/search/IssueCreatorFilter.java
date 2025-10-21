package com.issuemanagementsystem.issue.search;

import com.issuemanagementsystem.issue.Issue;
import com.issuemanagementsystem.users.Customer;

public class IssueCreatorFilter implements IssueSearchFilter {
    private final Customer customer;

    @Override
    public boolean matches(Issue issue) {
        return customer.equals(issue.getRaisedBy());
    }

    public IssueCreatorFilter(Customer customer) {
        this.customer = customer;
    }
}
