package com.ordermanagementsystem.service;

import java.util.List;
import java.util.Optional;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.constants.OrderStatus;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.exceptions.ItemsUnavailableException;
import com.ordermanagementsystem.interfaces.ICustomerService;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.interfaces.IOrderManagementService;
import com.ordermanagementsystem.interfaces.IOrderRepository;
import com.ordermanagementsystem.model.Address;
import com.ordermanagementsystem.model.Customer;
import com.ordermanagementsystem.model.Inventory;
import com.ordermanagementsystem.model.Item;
import com.ordermanagementsystem.model.Order;
import com.ordermanagementsystem.model.OrderItem;
import com.ordermanagementsystem.model.Seller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderManagementService implements IOrderManagementService {
    private final ICustomerService customerService;
    private final IOrderRepository orderRepository;
    private final IMarketplaceService marketplaceService;

    @Override
    public String createOrder(String customerId, List<Item> items, Address address) throws InvalidCustomerException, ItemsUnavailableException {
        Customer customer = customerService.getCustomerById(customerId);
        List<OrderItem> orderItems = items.stream().map(item -> new OrderItem(item)).toList();
        Order order = new Order(customerId, orderItems);
        Optional<Seller> seller = marketplaceService.findSellerAndReserve(items);
        if (seller.isEmpty()) throw new ItemsUnavailableException(ExceptionMessages.ITEMS_UNAVAILABLE_EXCEPTION);

        try {
            orderRepository.createOrder(order);
            order.setSeller(seller.get());
            order.setOrderStatus(OrderStatus.PLACED);
            System.out.println("Order placed successfully : " + order);
            return order.getOrderId();
        } catch (Exception e) {
            System.out.println("Order not placed" + e.getMessage());
            seller.get().getInventory().releaseItems(items);
            throw new RuntimeException("Order creation failed. Rolled back inventory", e);
        }
    }

    @Override
    public void updateOrder(String orderId, OrderStatus orderStatus) throws InvalidOrderException {
        Order order = orderRepository.getOrderById(orderId);
        order.setOrderStatus(orderStatus);
        Inventory sellerInventory = order.getSeller().getInventory();
        if (orderStatus.equals(OrderStatus.DELIVERY_FAILED) || orderStatus.equals(OrderStatus.CANCELLED)) {
            for (OrderItem item : order.getItemsOrdered()) {
                sellerInventory.releaseInventory(item.getItemId());
            }
        }
    }
}