package CoffeeShopBillingSystem;

public class Main {
    public static void main(String[] args) {
        Coffee coffee = new Cappuccino();
        System.out.println(coffee.getDescription() + " : Rs. " + coffee.getCost());

        coffee = new EspressoShot(coffee);
        System.out.println(coffee.getDescription() + " : Rs. " + coffee.getCost());

        coffee = new Cream(coffee);
        System.out.println(coffee.getDescription() + " : Rs. " + coffee.getCost());
    }
}