package com.ordermanagementsystem.interfaces;

import com.ordermanagementsystem.exceptions.CustomerAlreadyExistsException;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.model.Customer;

public interface ICustomerService {

    ICustomerRepository getCustomerRepository();

    void setCustomerRepository(ICustomerRepository customerRepository);

    void registerCustomer(Customer customer) throws CustomerAlreadyExistsException;

    Customer getCustomerById(String customerId) throws InvalidCustomerException;

}