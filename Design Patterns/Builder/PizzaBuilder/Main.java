public class Main {
    public static void main(String[] args) {
        Pizza pizza = new Pizza.PizzaBuilder()
                    .setCrust(Pizza.Crust.THIN)
                    .setSize(Pizza.Size.LARGE)
                    .addTopping(Pizza.Topping.OLIVES)
                    .addTopping(Pizza.Topping.JALAPENOS)
                    .addTopping(Pizza.Topping.PANEER)
                    .removeTopping(Pizza.Topping.JALAPENOS)
                    .build();
        
        System.out.println(pizza);
    }
}
