package com.ordermanagementsystem.repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.interfaces.IOrderRepository;
import com.ordermanagementsystem.model.Order;

public class OrderRepository implements IOrderRepository {
    private final Map<String, Order> orderById;
    private final Map<String, List<Order>> orderByCustomerId;

    @Override
    public void createOrder(Order order) {
        orderById.put(order.getOrderId(), order);
        orderByCustomerId.computeIfAbsent(order.getCustomerId(), orders -> new CopyOnWriteArrayList<>()).add(order);
    }

    @Override
    public Order getOrderById(String orderId) throws InvalidOrderException {
        if (!orderById.containsKey(orderId)) 
            throw new InvalidOrderException(ExceptionMessages.INVALID_ORDER_EXCEPTION);
    
        return orderById.get(orderId);
    }

    public OrderRepository() {
        this.orderById = new ConcurrentHashMap<>();
        this.orderByCustomerId = new ConcurrentHashMap<>();
    }
}
