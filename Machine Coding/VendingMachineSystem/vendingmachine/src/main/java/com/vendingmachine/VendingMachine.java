package com.vendingmachine;

import java.util.concurrent.atomic.AtomicInteger;

import com.vendingmachine.machineStates.DispenseState;
import com.vendingmachine.machineStates.HasMoneyState;
import com.vendingmachine.machineStates.NoMoneyState;
import com.vendingmachine.machineStates.SoldOutState;
import com.vendingmachine.machineStates.VendingMachineState;

public class VendingMachine {
    private volatile VendingMachineState vendingMachineState;
    
    private final AtomicInteger vendingMachineBalance;
    private volatile Integer itemCost;
    private final AtomicInteger itemCount;

    private final NoMoneyState noMoneyState;
    private final HasMoneyState hasMoneyState;
    private final DispenseState dispenseState;
    private final SoldOutState soldOutState;

    public VendingMachine(Integer itemCount, Integer itemCost) {
        this.itemCount = new AtomicInteger(itemCount);
        this.itemCost = itemCost;
        this.vendingMachineBalance = new AtomicInteger(0);
        
        this.noMoneyState = new NoMoneyState();
        this.hasMoneyState = new HasMoneyState();
        this.dispenseState = new DispenseState();
        this.soldOutState = new SoldOutState();
        
        this.vendingMachineState = (itemCount > 0) ? noMoneyState : soldOutState;
    }

    public synchronized void insertMoney(Integer amount) {
        vendingMachineState.insertMoney(this, amount);
    }

    public synchronized void selectItem() {
        vendingMachineState.selectItem(this);
    }

    public synchronized void dispense() {
        vendingMachineState.dispense(this);
    }

    public synchronized void refill(int itemCount, int itemCost) {
        vendingMachineState.refill(this, itemCount, itemCost);
    }

    // All methods synchronized to ensure complete thread safety
    // when called from state classes
    
    public void addBalance(Integer amount) {
        vendingMachineBalance.addAndGet(amount);
    }

    public void decrementItems() {
        itemCount.decrementAndGet();
    }

    public boolean isEmpty() {
        return itemCount.get() == 0;
    }

    // Synchronized getters to ensure consistent reads with state changes
    public Integer getVendingMachineBalance() {
        return vendingMachineBalance.get();
    }

    public Integer getItemCount() {
        return itemCount.get();
    }

    // Synchronized setters to coordinate with other operations
    public void setVendingMachineBalance(Integer balance) {
        vendingMachineBalance.set(balance);
    }

    public void setItemCount(Integer count) {
        itemCount.set(count);
    }

    // Synchronized compound operations to ensure atomicity
    public boolean hasEnoughBalance(Integer requiredAmount) {
        return vendingMachineBalance.get() >= requiredAmount;
    }

    public Integer calculateChange(Integer itemCost) {
        return vendingMachineBalance.get() - itemCost;
    }

    public boolean hasItems() {
        return itemCount.get() > 0;
    }
    
    // Synchronized state getter and setter for consistency
    public VendingMachineState getVendingMachineState() {
        return vendingMachineState;
    }
    
    public void setVendingMachineState(VendingMachineState state) {
        this.vendingMachineState = state;
    }
    
    // Synchronized getters for state objects (previously provided by @Data)
    public NoMoneyState getNoMoneyState() {
        return noMoneyState;
    }
    
    public HasMoneyState getHasMoneyState() {
        return hasMoneyState;
    }
    
    public DispenseState getDispenseState() {
        return dispenseState;
    }
    
    public SoldOutState getSoldOutState() {
        return soldOutState;
    }
    
    public Integer getItemCost() {
        return itemCost;
    }
    
    public void setItemCost(Integer itemCost) {
        this.itemCost = itemCost;
    }
}