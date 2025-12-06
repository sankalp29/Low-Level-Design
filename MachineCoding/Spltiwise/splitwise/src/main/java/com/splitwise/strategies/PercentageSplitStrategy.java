package com.splitwise.strategies;

import java.util.ArrayList;
import java.util.List;

import com.splitwise.constants.ExceptionConstants;
import com.splitwise.exceptions.InvalidUserSplitException;
import com.splitwise.model.Split;

public class PercentageSplitStrategy implements ISplitStrategy {

    @Override
    public List<Split> splitExpense(List<String> userIds, List<Double> userSplit, Double amount) throws throws InvalidUserSplitException {
        List<Split> splits = new ArrayList<>();
        int n = userIds.size();
        double totalPercentage = 0.0;
        for (int i = 0; i < n; i++) {
            splits.add(new Split(userIds.get(i), (userSplit.get(i) * amount) / 100.0));
            totalPercentage+=userSplit.get(i);
        }
        if (totalPercentage != 100) throw new InvalidUserSplitException(ExceptionConstants.INVALID_USER_SPLIT_EXCEPTION);
        
        return splits;
    }
}
