package CoffeeShopBillingSystem;

public class Cream extends AddOnDecorator {
    
    public Cream(Coffee coffee) {
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