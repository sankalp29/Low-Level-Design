public class App {
    public static void main(String[] args) throws Exception {
        // Test existing provider
        PaymentProvider provider = PaymentRegistry.getPaymentProvider("stripe");
        provider.pay(100);
        
        // Test new provider (added without modifying any existing code!)
        PaymentProvider googlePay = PaymentRegistry.getPaymentProvider("googlepay");
        googlePay.pay(250);
        
        // Test PayPal
        PaymentProvider paypal = PaymentRegistry.getPaymentProvider("paypal");
        paypal.pay(150);
    }
}
