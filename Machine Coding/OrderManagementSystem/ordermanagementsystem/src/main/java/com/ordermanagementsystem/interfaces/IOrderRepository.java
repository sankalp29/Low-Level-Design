package com.ordermanagementsystem.interfaces;

import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.model.Order;

public interface IOrderRepository {

    void createOrder(Order order);

    Order getOrderById(String orderId) throws InvalidOrderException;

}