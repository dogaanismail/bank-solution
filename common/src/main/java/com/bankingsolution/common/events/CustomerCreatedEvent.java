package com.bankingsolution.common.events;

import com.bankingsolution.cqrs.core.events.BaseEvent;

public class CustomerCreatedEvent extends BaseEvent {
    private String country;
}
