package com.ordermanagementsystem.service;

import java.util.List;
import java.util.Optional;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.constants.OrderStatus;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.exceptions.InvalidOrderState;
import com.ordermanagementsystem.exceptions.ItemsUnavailableException;
import com.ordermanagementsystem.interfaces.ICustomerService;
import com.ordermanagementsystem.interfaces.IMarketplaceService;
import com.ordermanagementsystem.interfaces.IOrderManagementService;
import com.ordermanagementsystem.interfaces.IOrderRepository;
import com.ordermanagementsystem.model.Address;
import com.ordermanagementsystem.model.Customer;
import com.ordermanagementsystem.model.Order;
import com.ordermanagementsystem.model.OrderItem;
import com.ordermanagementsystem.model.OrderLineItem;
import com.ordermanagementsystem.model.seller.Seller;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OrderManagementService implements IOrderManagementService {
    private final ICustomerService customerService;
    private final IOrderRepository orderRepository;
    private final IMarketplaceService marketplaceService;

    @Override
    public String createOrder(String customerId, List<OrderLineItem> items, Address address) throws InvalidCustomerException {
        Customer customer = customerService.getCustomerById(customerId);
        List<OrderItem> orderItems = items.stream().map(item -> new OrderItem(item.getItem(), item.getQuantity())).toList();
        Order order = new Order(customerId, orderItems);
        orderRepository.createOrder(order);
        return order.getOrderId();
    }

    @Override
    public String confirmOrder(String orderId) throws InvalidCustomerException, ItemsUnavailableException, InvalidOrderException {
        Order order = orderRepository.getOrderById(orderId);
        List<OrderItem> orderItems = order.getItemsOrdered();
        Optional<Seller> seller = marketplaceService.findSellerAndReserve(orderItems);
        
        try {
            if (!order.getOrderStatus().equals(OrderStatus.CREATED)) throw new InvalidOrderState(ExceptionMessages.INVALID_ORDER_STATE_EXCEPTION);
            if (seller.isEmpty()) throw new ItemsUnavailableException(ExceptionMessages.ITEMS_UNAVAILABLE_EXCEPTION);
            order.setSeller(seller.get());
            order.setOrderStatus(OrderStatus.PLACED);
            System.out.println("Order placed successfully : " + order + "\n");
            return order.getOrderId();
        } catch(ItemsUnavailableException e) {
            System.out.println(e.getMessage() + "\n");
            throw new ItemsUnavailableException(ExceptionMessages.ITEMS_UNAVAILABLE_EXCEPTION);
        } catch (Exception e) {
            System.out.println("Order not confirmed" + e.getMessage() + "\n");
            seller.get().releaseItems(orderItems);
            throw new RuntimeException("Order creation failed. Rolled back inventory", e);
        }
    }
    
    @Override
    public void updateOrder(String orderId, OrderStatus orderStatus) throws InvalidOrderException {
        Order order = orderRepository.getOrderById(orderId);
        order.setOrderStatus(orderStatus);
        Seller seller = order.getSeller();
        if (orderStatus.equals(OrderStatus.DELIVERY_FAILED) || orderStatus.equals(OrderStatus.CANCELLED)) {
            seller.releaseItems(order.getItemsOrdered());
        }
    }

    @Override
    public Order getOrderById(String orderId) throws InvalidOrderException {
        return orderRepository.getOrderById(orderId);
    }
}