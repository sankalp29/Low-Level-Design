package com.parkinglot.payment;

public class PaymentProcessor {

    public static void processPayment(double amount) {
        PaymentMedium paymentMedium = PaymentFactory.getPaymentMode();
        paymentMedium.processPayment(amount);
    }
}
