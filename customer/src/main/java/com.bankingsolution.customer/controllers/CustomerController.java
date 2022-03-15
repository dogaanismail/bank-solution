package com.bankingsolution.customer.controllers;

import com.bankingsolution.customer.dto.request.CustomerCreateRequest;
import com.bankingsolution.customer.service.CustomerService;
import com.bankingsolution.customer.service.ICustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
public class CustomerController {

    private final ICustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/createCustomer")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity createNewAccount(@RequestBody CustomerCreateRequest customerCreateRequest) {
        try {

            customerService.insertCustomer(customerCreateRequest);
            return ResponseEntity.ok( HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /*@GetMapping(value = "/get/{customerId}", produces = "application/json")
    public ResponseEntity getCustomerById(@RequestParam Long customerId) {
        try {
            ResponseModel

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    } */
}
