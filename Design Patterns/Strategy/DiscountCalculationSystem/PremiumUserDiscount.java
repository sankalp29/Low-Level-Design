package DiscountCalculationSystem;

public class PremiumUserDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double amount) {
        return amount - (20 * amount) / 100;
    }
}
