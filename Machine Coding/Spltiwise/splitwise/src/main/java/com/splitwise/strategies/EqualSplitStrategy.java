package com.splitwise.strategies;

import java.util.ArrayList;
import java.util.List;

import com.splitwise.model.Split;

public class EqualSplitStrategy implements ISplitStrategy {

    @Override
    public List<Split> splitExpense(List<String> userIds, List<Double> userSplit, Double amount) {
        List<Split> splits = new ArrayList<>();
        int n = userIds.size();
        Double personSplit = amount / n;
        for (String userId : userIds) {
            splits.add(new Split(userId, personSplit));
        }

        return splits;
    }

}
