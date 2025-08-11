package CoffeeShopBillingSystem;

public class EspressoShot extends AddOnDecorator {
    public EspressoShot(Coffee coffee) {
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
