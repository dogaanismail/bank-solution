package com.bankingsolution.account.cmd.port;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = "customer-service", url = "${spring.feign.customer.client}")
public interface ICustomerService {

    @GetMapping("/api/v1/customer/get/{customerId}")
    Object getCustomerById(@PathVariable Long customerId);
}
