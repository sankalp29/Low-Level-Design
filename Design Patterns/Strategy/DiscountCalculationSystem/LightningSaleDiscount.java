package DiscountCalculationSystem;

public class LightningSaleDiscount implements DiscountStrategy {

    @Override
    public double applyDiscount(double amount) {
        return amount - (5 * amount) / 100;
    }
}
