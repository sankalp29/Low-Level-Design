@PaymentProviderService("stripe")
public class StripePayment implements PaymentProvider {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via Stripe");
    }

    @Override
    public String getName() {
        return "stripe";
    }
}