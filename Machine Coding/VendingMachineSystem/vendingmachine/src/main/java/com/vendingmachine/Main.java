package com.vendingmachine;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Vending Machine Concurrency Test ===\n");
        
        // Test 1: Multiple customers trying to buy items simultaneously
        testConcurrentPurchases();
        
        // Test 2: Multiple customers inserting money simultaneously
        testConcurrentMoneyInsertion();
        
        // Test 3: Purchase while refilling
        testPurchaseWhileRefilling();
        
        System.out.println("\n=== All Concurrency Tests Completed ===");
    }
    
    /**
     * Test multiple customers trying to purchase the last few items simultaneously.
     * This tests the race condition where multiple threads might try to buy the last item.
     */
    private static void testConcurrentPurchases() throws InterruptedException {
        System.out.println("Test 1: Concurrent Purchases (Race for last items)");
        System.out.println("Setting up vending machine with 3 items at Rs. 20 each\n");
        
        VendingMachine vendingMachine = new VendingMachine(3, 20);
        int numberOfCustomers = 5; // More customers than items
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCustomers);
        CountDownLatch latch = new CountDownLatch(numberOfCustomers);
        AtomicInteger successfulPurchases = new AtomicInteger(0);
        
        // Each customer tries to buy one item
        for (int i = 1; i <= numberOfCustomers; i++) {
            final int customerId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Customer " + customerId + " attempting purchase...");
                    
                    // Insert money
                    vendingMachine.insertMoney(25);
                    
                    // Try to select item
                    vendingMachine.selectItem();
                    
                    // Try to dispense
                    vendingMachine.dispense();
                    
                    successfulPurchases.incrementAndGet();
                    System.out.println("Customer " + customerId + " completed purchase successfully!");
                    
                } catch (Exception e) {
                    System.out.println("Customer " + customerId + " failed: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("\nResult: " + successfulPurchases.get() + " successful purchases out of " + numberOfCustomers + " attempts");
        System.out.println("Remaining items: " + vendingMachine.getItemCount());
        System.out.println("Machine state: " + vendingMachine.getVendingMachineState().getClass().getSimpleName());
        System.out.println("----------------------------------------\n");
    }
    
    /**
     * Test multiple customers inserting money simultaneously.
     * This tests atomic operations on the balance field.
     */
    private static void testConcurrentMoneyInsertion() throws InterruptedException {
        System.out.println("Test 2: Concurrent Money Insertion");
        System.out.println("Multiple customers inserting money simultaneously\n");
        
        VendingMachine vendingMachine = new VendingMachine(10, 50);
        int numberOfCustomers = 10;
        ExecutorService executor = Executors.newFixedThreadPool(numberOfCustomers);
        CountDownLatch latch = new CountDownLatch(numberOfCustomers);
        
        // Each customer inserts Rs. 10
        for (int i = 1; i <= numberOfCustomers; i++) {
            final int customerId = i;
            executor.submit(() -> {
                try {
                    System.out.println("Customer " + customerId + " inserting Rs. 10");
                    vendingMachine.insertMoney(10);
                } catch (Exception e) {
                    System.out.println("Customer " + customerId + " failed: " + e.getMessage());
                } finally {
                    latch.countDown();
                }
            });
        }
        
        latch.await();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("\nExpected total balance: Rs. " + (numberOfCustomers * 10));
        System.out.println("Actual total balance: Rs. " + vendingMachine.getVendingMachineBalance());
        System.out.println("Balance integrity maintained: " + 
            (vendingMachine.getVendingMachineBalance() == numberOfCustomers * 10));
        System.out.println("----------------------------------------\n");
    }
    
    /**
     * Test concurrent purchase and refill operations.
     * This tests the synchronization of the main vending machine methods.
     */
    private static void testPurchaseWhileRefilling() throws InterruptedException {
        System.out.println("Test 3: Purchase While Refilling");
        System.out.println("Customer trying to purchase while maintenance refills machine\n");
        
        VendingMachine vendingMachine = new VendingMachine(1, 30);
        ExecutorService executor = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(2);
        
        // Customer thread
        executor.submit(() -> {
            try {
                System.out.println("Customer: Inserting money...");
                vendingMachine.insertMoney(35);
                
                Thread.sleep(100); // Small delay to allow refill to potentially interfere
                
                System.out.println("Customer: Selecting item...");
                vendingMachine.selectItem();
                
                System.out.println("Customer: Dispensing...");
                vendingMachine.dispense();
                
            } catch (Exception e) {
                System.out.println("Customer failed: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
        
        // Maintenance thread
        executor.submit(() -> {
            try {
                Thread.sleep(50); // Small delay to let customer start
                
                System.out.println("Maintenance: Attempting to refill...");
                vendingMachine.refill(5, 25);
                
            } catch (Exception e) {
                System.out.println("Maintenance operation result: " + e.getMessage());
            } finally {
                latch.countDown();
            }
        });
        
        latch.await();
        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);
        
        System.out.println("\nFinal state:");
        System.out.println("Items remaining: " + vendingMachine.getItemCount());
        System.out.println("Balance: Rs. " + vendingMachine.getVendingMachineBalance());
        System.out.println("Machine state: " + vendingMachine.getVendingMachineState().getClass().getSimpleName());
        System.out.println("----------------------------------------\n");
    }
}
