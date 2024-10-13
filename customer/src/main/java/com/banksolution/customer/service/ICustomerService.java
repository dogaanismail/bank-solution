package com.banksolution.customer.service;

import com.banksolution.customer.model.Customer;

import java.util.List;

public interface ICustomerService {

    void insertCustomer(Customer customer);

    Customer getCustomerById(Long customerId);

    List<Customer> getAllCustomer();
}
