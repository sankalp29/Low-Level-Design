@PaymentProviderService("paypal")
public class PaypalPayment implements PaymentProvider {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via Paypal");
    }

    @Override
    public String getName() {
        return "paypal";
    }
}
