package PaymentProcesingSystem;

public class Main {
    public static void main(String[] args) {
        PaymentProcessor.processPayment(100, PaymentModeTypes.CREDIT_CARD);
    }
}