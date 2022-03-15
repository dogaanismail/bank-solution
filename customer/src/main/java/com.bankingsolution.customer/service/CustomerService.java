package com.bankingsolution.customer.service;

import com.bankingsolution.customer.dto.request.CustomerCreateRequest;
import com.bankingsolution.customer.dto.response.CustomerResponse;
import com.bankingsolution.customer.mapper.CustomerMapper;
import com.bankingsolution.customer.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class CustomerService implements ICustomerService{

    private final CustomerMapper customerMapper;
    private final ModelMapper modelMapper;

    public CustomerService(CustomerMapper customerMapper,
                           ModelMapper modelMapper){
        this.customerMapper = customerMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public void insertCustomer(CustomerCreateRequest customerCreateRequest) {
        Customer customer = modelMapper.map(customerCreateRequest, Customer.class);
        customerMapper.insertCustomer(customer);
    }

    @Override
    public CustomerResponse getCustomerById(Long customerId) {
        Customer customer = customerMapper.getCustomerById(customerId);
        CustomerResponse response = modelMapper.map(customer, CustomerResponse.class);
        return response;
    }
}
