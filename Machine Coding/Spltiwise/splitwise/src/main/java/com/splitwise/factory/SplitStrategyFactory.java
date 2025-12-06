package com.splitwise.factory;

import com.splitwise.constants.SplitType;
import com.splitwise.strategies.EqualSplitStrategy;
import com.splitwise.strategies.ExactSplitStrategy;
import com.splitwise.strategies.ISplitStrategy;
import com.splitwise.strategies.PercentageSplitStrategy;

public class SplitStrategyFactory {
    public static ISplitStrategy getSplitStrategy(SplitType splitType) {
        switch (splitType) {
            case EQUAL:
                return new EqualSplitStrategy();
            case EXACT:
                return new ExactSplitStrategy();                
            case PERCENTAGE:
                return new PercentageSplitStrategy();
            default: return new EqualSplitStrategy();
        }
    }

    private SplitStrategyFactory() {}
}
