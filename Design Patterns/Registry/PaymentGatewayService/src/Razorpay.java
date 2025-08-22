@PaymentProviderService("razorpay")
public class Razorpay implements PaymentProvider {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via Razorpay");
    }

    @Override
    public String getName() {
        return "razorpay";
    }
}
