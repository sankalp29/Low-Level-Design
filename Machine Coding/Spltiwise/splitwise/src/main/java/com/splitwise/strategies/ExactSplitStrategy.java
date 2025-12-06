package com.splitwise.strategies;

import java.util.ArrayList;
import java.util.List;

import com.splitwise.constants.ExceptionConstants;
import com.splitwise.exceptions.InvalidUserSplitException;
import com.splitwise.model.Split;

public class ExactSplitStrategy implements ISplitStrategy {

    @Override
    public List<Split> splitExpense(List<String> userIds, List<Double> userSplit, Double amount) throws InvalidUserSplitException {
        List<Split> splits = new ArrayList<>();
        int n = userIds.size();
        double totalAmount = 0.0;
        for (int i = 0; i < n; i++) {
            totalAmount+=userSplit.get(i);
            splits.add(new Split(userIds.get(i), userSplit.get(i)));
        }
        if (totalAmount != amount) throw new InvalidUserSplitException(ExceptionConstants.INVALID_USER_SPLIT_EXCEPTION);
        
        return splits;
    }
}
