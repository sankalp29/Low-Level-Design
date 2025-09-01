package com.librarymanagement.payment;

import com.librarymanagement.interfaces.IPaymentMode;

public class CashPayment implements IPaymentMode {

    @Override
    public boolean processPayment(double amount) {
        System.out.println("Paying Rs. " + amount + " by Cash");
        return true;
    }
}
