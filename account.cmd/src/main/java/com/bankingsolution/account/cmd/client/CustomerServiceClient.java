package com.bankingsolution.account.cmd.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "customer-service", url = "${spring.feign.customer.client}")
public interface CustomerServiceClient {

    @GetMapping("/api/v1/customer/get/{customerId}")
    Object getCustomerById(@PathVariable Long customerId);
}
