package CoffeeShopBillingSystem;

public abstract class AddOnDecorator extends Coffee {
    private Coffee coffee;

    public AddOnDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public int getCost() {
        return coffee.getCost();
    }

    @Override
    public String getDescription() {
        return coffee.getDescription();
    }
}
