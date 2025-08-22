package com.vendingmachine.machineStates;

import com.vendingmachine.VendingMachine;

public class DispenseState implements VendingMachineState {

    @Override
    public void dispense(VendingMachine vendingMachine) {
        vendingMachine.decrementItems();
        System.out.println("Item dispensed...");
        if (vendingMachine.isEmpty()) {
            vendingMachine.setVendingMachineState(vendingMachine.getSoldOutState());
        } else {
            vendingMachine.setVendingMachineState(vendingMachine.getNoMoneyState());
        }
    }

    @Override
    public void selectItem(VendingMachine vendingMachine) {
        System.out.println("Item cannot be selected while machine is dispensing.");
    }  

    @Override
    public void insertMoney(VendingMachine vendingMachine, Integer amount) {
        System.out.println("Money cannot be inserted while machine is dispensing.");
    }

    @Override
    public void refill(VendingMachine vendingMachine, int itemCount, int itemCost) {
        System.out.println("Money cannot be refilled while machine is dispensing.");
    }
}
