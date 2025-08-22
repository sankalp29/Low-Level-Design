package com.vendingmachine.machineStates;

import com.vendingmachine.VendingMachine;
import com.vendingmachine.exceptions.IllegalCostException;

public class HasMoneyState implements VendingMachineState {

    @Override
    public void dispense(VendingMachine vendingMachine) {
        System.out.println("Select item before dispensing");
    }

    @Override
    public void selectItem(VendingMachine vendingMachine) {
        if (vendingMachine.hasEnoughBalance(vendingMachine.getItemCost())) {
            System.out.println("Item selected. Dispensing...");
            int excessBalance = vendingMachine.calculateChange(vendingMachine.getItemCost());
            if (excessBalance > 0) {
                System.out.println("Returning excess balance : Rs. " + excessBalance);
            }
            vendingMachine.setVendingMachineState(vendingMachine.getDispenseState());
        } else {
            System.out.println("Not enough balance. Returning money : Rs. " + vendingMachine.getVendingMachineBalance());
            vendingMachine.setVendingMachineState(vendingMachine.getNoMoneyState());
        }
        vendingMachine.setVendingMachineBalance(0);
    }  

    @Override
    public void insertMoney(VendingMachine vendingMachine, Integer amount) {
        if (amount <= 0) {
            throw new IllegalCostException("Item cost cannot be <= zero. Please pass in the correct cost");
        }
        vendingMachine.addBalance(amount);
        System.out.println("Additional money added. Rs. " + amount);
    }

    @Override
    public void refill(VendingMachine vendingMachine, int itemCount, int itemCost) {
        System.out.println("Cannot refill machine while user is buying");
    }
}