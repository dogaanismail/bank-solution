package com.bankingsolution.customer.controllers;

import com.bankingsolution.customer.dto.request.CustomerCreateRequest;
import com.bankingsolution.customer.dto.response.CustomerResponse;
import com.bankingsolution.customer.process.CustomerProcess;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final CustomerProcess customerProcess;

    public CustomerController(CustomerProcess customerProcess) {
        this.customerProcess = customerProcess;
    }

    @PostMapping("/createCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createAccount(@RequestBody CustomerCreateRequest customerCreateRequest) {
        try {
            customerProcess.create(customerCreateRequest);
            return ResponseEntity.ok( HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/get/{customerId}", produces = "application/json")
    public ResponseEntity getCustomerById(@RequestParam Long customerId) {
        try {
            CustomerResponse response = customerProcess.getById(customerId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(value = "/getCustomers", produces = "application/json")
    public ResponseEntity getCustomers() {
        try {
            List<CustomerResponse> response = customerProcess.getAllCustomers();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
