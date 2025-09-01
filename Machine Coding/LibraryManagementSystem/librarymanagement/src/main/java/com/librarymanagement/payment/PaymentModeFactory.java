package com.librarymanagement.payment;

import java.util.Scanner;

import com.librarymanagement.constants.PaymentMode;
import com.librarymanagement.exceptions.IllegalPaymentModeException;
import com.librarymanagement.interfaces.IPaymentMode;
public class PaymentModeFactory {
    public static IPaymentMode getPaymentMode(PaymentMode paymentMode) throws IllegalPaymentModeException {
        switch (paymentMode) {
            case CASH: return new CashPayment();
            case CARD: {
                Scanner in = new Scanner(System.in);
                System.out.println("Enter your card number:\n");
                String cardNumber = in.next();
                System.out.println("Enter your PIN:\n");
                String pin = in.next();
                in.close();
                return new CardPayment(cardNumber, pin);
            }
            case UPI: {
                Scanner in = new Scanner(System.in);
                System.out.println("Enter your UPI ID:\n");
                String upiId = in.next();
                System.out.println("Enter your PIN:\n");
                String pin = in.next();
                in.close();
                return new UPIPayment(upiId, pin);
            }
            default: throw new IllegalPaymentModeException("Illegal payment mode selected.");
        }
    }
}
