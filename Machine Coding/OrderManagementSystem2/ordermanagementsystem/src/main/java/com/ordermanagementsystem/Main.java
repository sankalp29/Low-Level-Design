package com.ordermanagementsystem;

import java.util.List;

import com.ordermanagementsystem.exceptions.CustomerAlreadyExistsException;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.exceptions.InvalidQuantityException;
import com.ordermanagementsystem.exceptions.ItemsUnavailableException;
import com.ordermanagementsystem.interfaces.ICustomerService;
import com.ordermanagementsystem.interfaces.IInventoryService;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.interfaces.IOrderManagementService;
import com.ordermanagementsystem.interfaces.IOrderRepository;
import com.ordermanagementsystem.model.Address;
import com.ordermanagementsystem.model.Customer;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.Marketplace;
import com.ordermanagementsystem.model.OrderLineItem;
import com.ordermanagementsystem.repository.CustomerRepository;
import com.ordermanagementsystem.repository.OrderRepository;
import com.ordermanagementsystem.service.CustomerService;
import com.ordermanagementsystem.service.InventoryService;
import com.ordermanagementsystem.service.MarketplaceService;
import com.ordermanagementsystem.service.OrderManagementService;

public class Main {
    public static void main(String[] args) {
        Marketplace marketplace = new Marketplace();
        IMarketplaceService marketplaceService = new MarketplaceService(marketplace);
        ICustomerService customerService = new CustomerService(new CustomerRepository());
        IInventoryService inventoryService = new InventoryService(marketplaceService);
        IOrderRepository orderRepository = new OrderRepository();
        IOrderManagementService orderManagementService = new OrderManagementService(customerService, orderRepository, marketplaceService);
        
        Address address1 = new Address("1", "A1", "123456", "NSK", "MH", "IND");
        Address address2 = new Address("1", "A1", "123456", "NSK", "MH", "IND");
        Customer sankalp = new Customer("Sankalp", "sankalp@email.com", "7507491598", address1);
        Customer janvi = new Customer("Janvi", "janvi@email.com", "9518566932", address2);
        
        try {
            customerService.registerCustomer(sankalp);
            customerService.registerCustomer(janvi);
        } catch (CustomerAlreadyExistsException ex) {
            System.out.println(ex.getMessage());
        }
        
        Item shoes = new Item("AJ-1", "Nike", "Air Jordan 1", 18000.0);
        Item socks = new Item("Bamboo Socks", "Heelium", "Bamboo Socks ultra light", 200.0);

        Runnable runnable1 = () -> {
            String orderId1;
            try {
                OrderLineItem oli = new OrderLineItem(shoes, 3);
                orderId1 = orderManagementService.createOrder(sankalp.getCustomerId(), List.of(oli), sankalp.getAddress());
                System.out.println("Before placing order1: " + orderManagementService.getOrderById(orderId1) + "\n ");
                
                orderManagementService.confirmOrder(orderId1);
                System.out.println("After placing order1: " + orderManagementService.getOrderById(orderId1) + "\n ");
            } catch (RuntimeException | InvalidCustomerException | ItemsUnavailableException | InvalidOrderException e) {
            }
            
        };
        
        Runnable runnable2 = () -> {
            try {
                OrderLineItem oli = new OrderLineItem(shoes, 2);
                String orderId2 = orderManagementService.createOrder(janvi.getCustomerId(), List.of(oli), janvi.getAddress());
                System.out.println("Before placing order2: " + orderManagementService.getOrderById(orderId2) + "\n ");

                orderManagementService.confirmOrder(orderId2);
                System.out.println("After placing order2: " + orderManagementService.getOrderById(orderId2) + "\n ");
            } catch (InvalidCustomerException | ItemsUnavailableException | InvalidOrderException ex) {
            }
        };
        
        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        
        try {
            inventoryService.addNewItem(shoes, 2);
            inventoryService.addNewItem(socks, 2);
        } catch (InvalidQuantityException e) {
            e.printStackTrace();
        }
        
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}