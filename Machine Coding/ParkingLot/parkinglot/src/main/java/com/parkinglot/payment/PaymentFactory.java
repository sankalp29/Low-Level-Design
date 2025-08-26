package com.parkinglot.payment;

import java.util.InputMismatchException;
import java.util.Scanner;

public class PaymentFactory {
    public static PaymentMedium getPaymentMode() {
        PaymentMode paymentMode = selectPaymentMode();
        switch (paymentMode) {
            case UPI: return new UPI();
            case CREDIT_CARD : return new CreditCard();
            case CASH : return new Cash();
            default: throw new AssertionError();
        }
    }

    private static PaymentMode selectPaymentMode() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            try {
                System.out.println("Select a Payment Mode from:");
                for (PaymentMode paymentMode : PaymentMode.values()) {
                    System.out.println(paymentMode.ordinal() + " : " + paymentMode.name());
                }
                System.out.println();
                System.out.print("Enter choice: ");
                int number = scanner.nextInt();

                if (number >= 0 && number < PaymentMode.values().length) {
                    return PaymentMode.values()[number];
                } else {
                    System.out.println("Invalid choice. Please select a valid number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // clear the invalid input
            }
        }
    }
}
