package DiscountCalculationSystem;

import java.util.ArrayList;
import java.util.List;

public class CompositeDiscount implements DiscountStrategy {

    private List<DiscountStrategy> discountsApplicable;

    public CompositeDiscount() {
        discountsApplicable = new ArrayList<>();
    }

    public CompositeDiscount addDiscount(DiscountStrategy discountStrategy) {
        discountsApplicable.add(discountStrategy);
        return this;
    }

    @Override
    public double applyDiscount(double amount) {
        for (DiscountStrategy strategy : discountsApplicable) {
            amount = strategy.applyDiscount(amount);
        }

        return amount;
    }
    
}
