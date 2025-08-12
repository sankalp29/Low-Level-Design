package PizzaOrderingSystem;

public class Sauce extends PizzaDecorator {
    
    public Sauce(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Sauces";
    }
}
