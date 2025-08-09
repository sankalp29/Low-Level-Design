package PaymentProcesingSystem;

public class CreditCard implements PaymentMode {
    private String cardNumber;

    public CreditCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via Credit Card : " + cardNumber);
    }
}