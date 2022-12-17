package com.bankingsolution.customer.service;

import com.bankingsolution.customer.mapper.CustomerMapper;
import com.bankingsolution.customer.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper) {
        this.customerMapper = customerMapper;
    }

    @Override
    public void insertCustomer(Customer customer) {
        customerMapper.insertCustomer(customer);
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        return customerMapper.getCustomerById(customerId);
    }

    @Override
    public List<Customer> getAllCustomer() {
        return customerMapper.getAllCustomers();
    }
}
