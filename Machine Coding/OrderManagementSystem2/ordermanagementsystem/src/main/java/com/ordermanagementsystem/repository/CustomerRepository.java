package com.ordermanagementsystem.repository;

import java.util.HashMap;
import java.util.Map;

import com.ordermanagementsystem.constants.ExceptionMessages;
import com.ordermanagementsystem.exceptions.CustomerAlreadyExistsException;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.interfaces.ICustomerRepository;
import com.ordermanagementsystem.model.Customer;

public class CustomerRepository implements ICustomerRepository {
    private final Map<String, Customer> customerById;

    @Override
    public void registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        if (customerById.containsKey(customer.getCustomerId())) 
            throw new CustomerAlreadyExistsException(ExceptionMessages.CUSTOMER_ALREADY_EXISTS_EXCEPTION);

        customerById.put(customer.getCustomerId(), customer);
    }

    @Override
    public Customer getCustomerById(String customerId) throws InvalidCustomerException {
        if (!customerById.containsKey(customerId))
            throw new InvalidCustomerException(ExceptionMessages.INVALID_CUSTOMER_EXCEPTION);

        return customerById.get(customerId);
    }

    public CustomerRepository() {
        this.customerById = new HashMap<>();
    }
}
