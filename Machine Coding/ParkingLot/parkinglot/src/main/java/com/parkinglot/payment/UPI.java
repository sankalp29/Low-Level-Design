package com.parkinglot.payment;

public class UPI implements PaymentMedium {

    @Override
    public void processPayment(double amount) {
        System.out.println("Processed payment via UPI");
    }
}
