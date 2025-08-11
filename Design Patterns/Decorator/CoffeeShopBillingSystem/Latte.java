package CoffeeShopBillingSystem;

public class Latte extends Coffee {

    @Override
    public int getCost() {
        return 220;
    }

    @Override
    public String getDescription() {
        return "Latte";
    }
}