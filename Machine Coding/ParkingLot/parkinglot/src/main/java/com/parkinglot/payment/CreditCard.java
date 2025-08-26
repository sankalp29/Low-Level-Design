package com.parkinglot.payment;

public class CreditCard implements PaymentMedium {
    private String cardNumber;
    private String cvv;
    
    @Override
    public void processPayment(double amount) {
        System.out.println("Processed payment via Credit card");
    }
}
