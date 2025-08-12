package PizzaOrderingSystem;

public class Main {
    public static void main(String[] args) {
        Pizza pizza = new GourmetPizza();
        System.out.println(pizza.getDescription());

        pizza = new Cheese(pizza);
        System.out.println(pizza.getDescription());

        pizza = new Topping(pizza);
        System.out.println(pizza.getDescription());
    }
}