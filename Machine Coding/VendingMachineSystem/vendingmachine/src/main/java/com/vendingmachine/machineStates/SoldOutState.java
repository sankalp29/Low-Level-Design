package com.vendingmachine.machineStates;

import com.vendingmachine.VendingMachine;
import com.vendingmachine.exceptions.IllegalCostException;

public class SoldOutState implements VendingMachineState {

    @Override
    public void dispense(VendingMachine vendingMachine) {
        System.out.println("Machine is empty. Cannot dispense anything.");
    }

    @Override
    public void selectItem(VendingMachine vendingMachine) {
        System.out.println("Machine is empty. Cannot select any item.");
    }  

    @Override
    public void insertMoney(VendingMachine vendingMachine, Integer amount) {
        System.out.println("Machine is empty. Cannot insert money till machine is refilled.");
    }

    @Override
    public void refill(VendingMachine vendingMachine, int itemCount, int itemCost) {
        if (itemCost <= 0) {
            throw new IllegalCostException("Item cost cannot be <= zero. Please pass in the correct cost");
        }
        if (itemCount <= 0) {
            throw new IllegalArgumentException("Item count cannot be <= zero. Please pass in the correct count");
        }
        
        vendingMachine.setItemCount(itemCount);
        vendingMachine.setItemCost(itemCost);
        if (itemCount != 0) {
            vendingMachine.setVendingMachineState(vendingMachine.getNoMoneyState());    
        }
    } 
}
