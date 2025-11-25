package com.ordermanagementsystem.interfaces;

import java.util.List;

import com.ordermanagementsystem.constants.OrderStatus;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.exceptions.ItemsUnavailableException;
import com.ordermanagementsystem.model.Address;
import com.ordermanagementsystem.model.Order;
import com.ordermanagementsystem.model.OrderLineItem;

public interface IOrderManagementService {

    String confirmOrder(String orderId)
            throws InvalidCustomerException, ItemsUnavailableException, InvalidOrderException;

    String createOrder(String customerId, List<OrderLineItem> items, Address address) throws InvalidCustomerException;

    void updateOrder(String orderId, OrderStatus orderStatus) throws InvalidOrderException;

    Order getOrderById(String orderId) throws InvalidOrderException;

}