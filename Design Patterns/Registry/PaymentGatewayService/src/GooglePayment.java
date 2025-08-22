@PaymentProviderService("googlepay")
public class GooglePayment implements PaymentProvider {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via Google Pay");
    }

    @Override
    public String getName() {
        return "googlepay";
    }
}
