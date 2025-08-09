package DiscountCalculationSystem;

public class FirstTimeUserDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double amount) {
        return amount - (10 * amount) / 100;
    }
}
