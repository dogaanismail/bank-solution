package com.bankingsolution.customer.service;

import com.bankingsolution.customer.dto.request.CustomerCreateRequest;
import com.bankingsolution.customer.dto.response.CustomerResponse;
import com.bankingsolution.customer.mapper.CustomerMapper;
import com.bankingsolution.customer.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService implements ICustomerService{
    final CustomerMapper customerMapper;

    public CustomerService(CustomerMapper customerMapper){
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
