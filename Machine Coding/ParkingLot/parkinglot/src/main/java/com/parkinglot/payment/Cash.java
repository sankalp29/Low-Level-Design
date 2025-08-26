package com.parkinglot.payment;

public class Cash implements PaymentMedium {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processed payment via Cash");
    }
}
