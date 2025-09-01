package com.librarymanagement.payment;

import com.librarymanagement.constants.PaymentMode;
import com.librarymanagement.exceptions.IllegalPaymentModeException;
import com.librarymanagement.interfaces.IPaymentMode;

import lombok.AllArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Setter
public class PaymentProcessor {
    private PaymentMode paymentMode;

    public boolean processPayment(double amount) {
        try {
            IPaymentMode payment = PaymentModeFactory.getPaymentMode(paymentMode);
            return payment.processPayment(amount);
        } catch (IllegalPaymentModeException e) {
            System.err.println("Error processing payment: " + e.getMessage());
            return false;
        }
    }
}