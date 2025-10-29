package com.elevatorsystem.button;

public class Button {
    private ButtonStatus buttonStatus;

    public Button() {
        this.buttonStatus = ButtonStatus.RELEASED;
    }

    public void press() {
        if (buttonStatus.equals(ButtonStatus.PRESSED)) {
            System.out.println("Button already pressed");
            return;
        }
        buttonStatus = ButtonStatus.PRESSED;
    }

    public void release() {
        if (buttonStatus.equals(ButtonStatus.RELEASED)) {
            System.out.println("Button already released");
            return;
        }
        buttonStatus = ButtonStatus.RELEASED;
    }
}
