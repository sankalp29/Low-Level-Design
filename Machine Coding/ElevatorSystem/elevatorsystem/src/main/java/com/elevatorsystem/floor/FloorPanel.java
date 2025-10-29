package com.elevatorsystem.floor;

import com.elevatorsystem.button.Button;

import lombok.Getter;

@Getter
public class FloorPanel {
    private Button upButton;
    private Button downButton;

    public FloorPanel() {
        this.upButton = new Button();
        this.downButton = new Button();
    }

    public void pressUpButton() {
        upButton.press();
        System.out.println("Up button pressed on floor panel");
    }

    public void pressDownButton() {
        downButton.press();
        System.out.println("Down button pressed on floor panel");
    }

    public void releaseUpButton() {
        upButton.release();
    }

    public void releaseDownButton() {
        downButton.release();
    }
}
