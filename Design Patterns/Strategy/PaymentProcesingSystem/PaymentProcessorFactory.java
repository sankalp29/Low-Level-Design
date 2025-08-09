package PaymentProcesingSystem;

public class PaymentProcessorFactory {
    public static PaymentMode getPaymentMode(PaymentModeTypes paymentMode) {
        switch (paymentMode) {
            case CASH:
                return new Cash();
            case CREDIT_CARD:
                return new CreditCard("2222 2222 2222");
            case UPI:
                return new UPI("name@okicici");
            default:
                throw new IllegalArgumentException("Illegal payment mode selected");
        }
    }
}
