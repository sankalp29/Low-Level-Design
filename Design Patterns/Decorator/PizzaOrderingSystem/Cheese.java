package PizzaOrderingSystem;

public class Cheese extends PizzaDecorator {
    
    public Cheese(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + Cheese";
    }
}
