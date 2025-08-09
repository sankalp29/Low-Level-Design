package PaymentProcesingSystem;

public class Cash implements PaymentMode {

    @Override
    public void pay(double amount) {
        System.out.println("Paying " + amount + " via Cash");
    } 
}
