package com.vendingmachine.machineStates;

import com.vendingmachine.VendingMachine;

public interface VendingMachineState {
    public void insertMoney(VendingMachine vendingMachine, Integer amount);
    public void selectItem(VendingMachine vendingMachine);
    public void dispense(VendingMachine vendingMachine);
    public void refill(VendingMachine vendingMachine, int itemCount, int itemCost);
}
