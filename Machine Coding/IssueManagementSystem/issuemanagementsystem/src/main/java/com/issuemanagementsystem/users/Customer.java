package com.issuemanagementsystem.users;

import com.issuemanagementsystem.issue.IssueStatus;

public class Customer extends User {

    public Customer(String name, String email) {
        super(name, email);
    }

    public void notify(IssueStatus issueStatus, String updateNotes) {
        System.out.println("[Customer] " + this + "Issue status has been updated to : " + issueStatus + ".\nDetails: " + updateNotes);
    }
}
