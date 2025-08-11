package CoffeeShopBillingSystem;

public class Milk extends AddOnDecorator {
    public Milk(Coffee coffee) {
        super(coffee);
    }

    @Override
    public int getCost() {
        return super.getCost() + 50;
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Espresso Shot";
    } 
}
