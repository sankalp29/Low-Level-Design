package com.ordermanagementsystem.service;

import com.ordermanagementsystem.exceptions.CustomerAlreadyExistsException;
import com.ordermanagementsystem.exceptions.InvalidCustomerException;
import com.ordermanagementsystem.interfaces.ICustomerRepository;
import com.ordermanagementsystem.interfaces.ICustomerService;
import com.ordermanagementsystem.model.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private ICustomerRepository customerRepository;

    @Override
    public void registerCustomer(Customer customer) throws CustomerAlreadyExistsException {
        customerRepository.registerCustomer(customer);
    }

    @Override
    public Customer getCustomerById(String customerId) throws InvalidCustomerException {
        return customerRepository.getCustomerById(customerId);
    }
}
