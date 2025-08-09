package PaymentProcesingSystem;

public class UPI implements PaymentMode {

    private final String upiId;

    public UPI(String upiId) {
        this.upiId = upiId;
    }

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via UPI with Id: " + upiId);
    }
}
