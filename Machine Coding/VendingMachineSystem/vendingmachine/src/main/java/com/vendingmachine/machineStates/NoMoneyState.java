package com.vendingmachine.machineStates;

import com.vendingmachine.VendingMachine;

public class NoMoneyState implements VendingMachineState {

    @Override
    public void dispense(VendingMachine vendingMachine) {
        System.out.println("No money in the machine. Insert money first");
    }

    @Override
    public void insertMoney(VendingMachine vendingMachine, Integer amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Illegal amount insert attempt. Amount cannot be negative or zero.");
        }
        System.out.println("Added money : Rs. " +amount);
        vendingMachine.addBalance(amount);
        vendingMachine.setVendingMachineState(vendingMachine.getHasMoneyState());
    }

    @Override
    public void refill(VendingMachine vendingMachine, int itemCount, int itemCost) {
        System.out.println("Cannot refill now. Items still present in the machine");
    }

    @Override
    public void selectItem(VendingMachine vendingMachine) {
        System.out.println("Cannot select item without inserting money.");
    }   
}
