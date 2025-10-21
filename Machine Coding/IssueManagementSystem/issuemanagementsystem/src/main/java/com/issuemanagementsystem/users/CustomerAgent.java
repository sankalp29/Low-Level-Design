package com.issuemanagementsystem.users;

import java.util.List;

import com.issuemanagementsystem.issue.IssueType;

import lombok.Getter;

@Getter
public class CustomerAgent extends User {
    private final List<IssueType> issueTypesHandler;
    private boolean isOccupied;

    public CustomerAgent(String name, String email, List<IssueType> issueTypesHandler) {
        super(name, email);
        this.issueTypesHandler = issueTypesHandler;
        this.isOccupied = false;
    }

    public void notify(String message) {
        System.out.println("[Agent] " + this + " : " + message);
    }

    public void setOccupied() {
        this.isOccupied = true;
    }

    public void setFree() {
        this.isOccupied = false;
    }
}
