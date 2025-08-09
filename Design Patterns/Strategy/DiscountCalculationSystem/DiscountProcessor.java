package DiscountCalculationSystem;

public class DiscountProcessor {
    public static double applyDiscount(double amount, DiscountStrategy discountStrategy) {
        return discountStrategy.applyDiscount(amount);
    }
}
