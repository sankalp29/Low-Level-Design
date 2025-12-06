package com.splitwise.strategies;

import java.util.List;

import com.splitwise.model.Split;

public interface ISplitStrategy {
    List<Split> splitExpense(List<String> users, List<Double> userSplit, Double amount);
}
