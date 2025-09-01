package com.librarymanagement.payment;

import com.librarymanagement.interfaces.IPaymentMode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UPIPayment implements IPaymentMode {
    private final String upiId;
    private String pin;

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying Rs. " +amount + " via UPI ID: " + upiId);
        return true;
    }
}
