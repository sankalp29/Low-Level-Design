package com.ordermanagementsystem.interfaces;

import java.util.List;

import com.ordermanagementsystem.constants.OrderStatus;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.exceptions.InvalidOrderException;
import com.ordermanagementsystem.exceptions.ItemsUnavailableException;
import com.ordermanagementsystem.model.Address;
import com.ordermanagementsystem.model.Item;

public interface IOrderManagementService {

    String createOrder(String customerId, List<Item> items, Address address)
            throws InvalidCustomerException, ItemsUnavailableException;

    void updateOrder(String orderId, OrderStatus orderStatus) throws InvalidOrderException;

}