package CoffeeShopBillingSystem;

public class Cappuccino extends Coffee {
    @Override
    public String getDescription() {
        return "Cappuccino";
    }

    @Override
    public int getCost() {
        return 200;
    }
}
