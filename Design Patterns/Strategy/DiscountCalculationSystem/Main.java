package DiscountCalculationSystem;

public class Main {
    public static void main(String[] args) {
        double amount1 = DiscountProcessor.applyDiscount(1000, new FirstTimeUserDiscount());
        double amount2 = DiscountProcessor.applyDiscount(1000, 
                            new CompositeDiscount()
                            .addDiscount(new FirstTimeUserDiscount())
                            .addDiscount(new LightningSaleDiscount()));
        
        double amount3 = DiscountProcessor.applyDiscount(1000, 
                            new CompositeDiscount()
                            .addDiscount(new PremiumUserDiscount())
                            .addDiscount(new LightningSaleDiscount()));

        System.out.println("Amount1 = " + amount1);
        System.out.println("Amount2 = " + amount2);
        System.out.println("Amount3 = " + amount3);
    }
}
