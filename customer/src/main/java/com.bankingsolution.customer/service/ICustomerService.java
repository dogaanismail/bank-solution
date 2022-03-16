package com.bankingsolution.customer.service;

import com.bankingsolution.customer.dto.request.CustomerCreateRequest;
import com.bankingsolution.customer.dto.response.CustomerResponse;
import com.bankingsolution.customer.model.Customer;

public interface ICustomerService {

    void insertCustomer(Customer customer);

    Customer getCustomerById(Long customerId);
}
