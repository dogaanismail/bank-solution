package com.bankingsolution.customer.model;

import org.springframework.stereotype.Component;

@Component
public class Customer {

    private Long customerId;

    private String country;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
