package com.librarymanagement.payment;

import com.librarymanagement.interfaces.IPaymentMode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CardPayment implements IPaymentMode {
    private final String cardNumber;
    private String pin;

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying Rs. " + amount + " by Card");
        return true;
    }
}
