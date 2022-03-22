package com.bankingsolution.customer.dto.request;

public class CustomerCreateRequest {

    private String country;

    public String getCountry() {
        return country;
    }

    public CustomerCreateRequest setCountry(String country) {
        this.country = country;
        return this;
    }
}
