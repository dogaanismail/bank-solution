package com.bankingsolution.common.events;

import com.bankingsolution.cqrs.core.events.BaseEvent;
import java.math.BigDecimal;

public class AccountOpenedEvent extends BaseEvent {
    private long CustomerId;
    private BigDecimal initialBalance;
}
