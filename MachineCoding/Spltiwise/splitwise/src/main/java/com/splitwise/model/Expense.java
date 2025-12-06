package com.splitwise.model;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Expense {
    private static Integer expenseCount = 0;
    private final String id;
    private String description;
    private Double amount;
    private String paidBy;
    private final LocalDateTime createdAt;

    public Expense(String paidBy, Double amount, String description) {
        this.id = "Expense-" + (++expenseCount);
        this.paidBy = paidBy;
        this.amount = amount;
        this.description = description;
        this.createdAt = LocalDateTime.now();
    }
}
