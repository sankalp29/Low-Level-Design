package PaymentProcesingSystem;

public class PaymentProcessor {
    public static boolean processPayment(double amount, PaymentModeTypes paymentModeType) {
        PaymentMode paymentMode = PaymentProcessorFactory.getPaymentMode(paymentModeType);
        paymentMode.pay(amount);
        
        return true;
    }
}
