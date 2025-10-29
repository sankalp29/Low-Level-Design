package com.flightmanagementsystem.payment;

public class PaymentService {
    public boolean processPayment(Double amount) {
        System.out.println("Processing payment of Rs. " + amount + "\n");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Processed payment successfully");
        return true;
    }

    public boolean processRefund(Double amount) {
        System.out.println("Processing refund of Rs. " + amount + "\n");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Processed refund successfully" + "\n");
        return true;
    }
}
