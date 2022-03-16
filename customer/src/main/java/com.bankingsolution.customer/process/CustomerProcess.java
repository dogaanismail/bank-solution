package com.bankingsolution.customer.process;

import com.bankingsolution.customer.dto.request.CustomerCreateRequest;
import com.bankingsolution.customer.dto.response.CustomerResponse;
import com.bankingsolution.customer.model.Customer;
import com.bankingsolution.customer.rabbitmq.data.CustomerMessage;
import com.bankingsolution.customer.rabbitmq.publisher.CustomerMessagePublisher;
import com.bankingsolution.customer.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class CustomerProcess {

    private final ModelMapper modelMapper;
    private final CustomerService customerService;
    private final CustomerMessagePublisher customerMessagePublisher;

    public CustomerProcess(ModelMapper modelMapper,
                           CustomerService customerService,
                           CustomerMessagePublisher customerMessagePublisher) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
        this.customerMessagePublisher = customerMessagePublisher;
    }

    public void create(CustomerCreateRequest customerCreateRequest){
        try{
            Customer customer = convertToCustomerModel(customerCreateRequest);
            customerService.insertCustomer(customer);
            customerMessagePublisher.publishAccountCreatedEvent("New customer has been added!");
        }catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    public CustomerResponse getById(long customerId){
        Customer customer = customerService.getCustomerById(customerId);
        return modelMapper.map(customer,CustomerResponse.class );
    }

    private Customer convertToCustomerModel(CustomerCreateRequest customerCreateRequest) {
        return modelMapper.map(customerCreateRequest, Customer.class);
    }
}
