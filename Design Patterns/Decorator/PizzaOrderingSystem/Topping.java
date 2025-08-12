package PizzaOrderingSystem;

public class Topping extends PizzaDecorator {
    
    public Topping(Pizza pizza) {
        super(pizza);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " + Toppings";
    }
}
