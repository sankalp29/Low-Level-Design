package com.ordermanagementsystem;

import java.util.List;

import com.ordermanagementsystem.exceptions.CustomerAlreadyExistsException;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.exceptions.InvalidItemException;
import com.ordermanagementsystem.exceptions.ItemsUnavailableException;
import com.ordermanagementsystem.exceptions.UnknownSellerException;
import com.ordermanagementsystem.interfaces.ICustomerService;
import com.ordermanagementsystem.interfaces.IInventoryService;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.interfaces.IOrderManagementService;
import com.ordermanagementsystem.interfaces.IOrderRepository;
import com.ordermanagementsystem.model.Address;
import com.ordermanagementsystem.model.Customer;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.Marketplace;
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
                orderId1 = orderManagementService.createOrder(sankalp.getCustomerId(), List.of(shoes), sankalp.getAddress());
                System.out.println("OrderId1 = " +orderId1);
            } catch (InvalidCustomerException | ItemsUnavailableException ex) {
            }
            
        };
        
        Runnable runnable2 = () -> {
            try {
                String orderId2 = orderManagementService.createOrder(janvi.getCustomerId(), List.of(shoes), janvi.getAddress());
                System.out.println("OrderId2 = " +orderId2);
            } catch (InvalidCustomerException | ItemsUnavailableException ex) {
            }
        };
        
        Thread t1 = new Thread(runnable1);
        Thread t2 = new Thread(runnable2);
        
        inventoryService.addNewItem(shoes, 2);
        inventoryService.addNewItem(socks, 2);
        
        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            inventoryService.getAvailableInventory("26f49e6c-679a-4041-86a5-f243047cb26d", "26f49e6c-679a-4041-86a5-f243047cb26d");
        } catch (InvalidItemException | UnknownSellerException e) {
            e.printStackTrace();
        }
    }
}