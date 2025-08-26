package com.parkinglot.payment;

public interface PaymentMedium {
    void processPayment(double amount);
}
