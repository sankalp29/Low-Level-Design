package com.splitwise.strategies;

import java.util.ArrayList;
import java.util.List;

import com.splitwise.model.Split;

public class ExactSplitStrategy implements ISplitStrategy {

    @Override
    public List<Split> splitExpense(List<String> userIds, List<Double> userSplit, Double amount) {
        List<Split> splits = new ArrayList<>();
        int n = userIds.size();

        for (int i = 0; i < n; i++) {
            splits.add(new Split(userIds.get(i), userSplit.get(i)));
        }
        
        return splits;
    }
}
