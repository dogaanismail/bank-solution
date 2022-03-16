package com.bankingsolution.customer.rabbitmq.data;

public class CustomerMessage {
    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerMessage setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }
}
