package com.bankingsolution.common.events;

import com.bankingsolution.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionCreatedEvent extends BaseEvent implements Serializable {
    private String accountId;
    private BigDecimal amount;
    private String currency;
    private String direction;
    private String description;
    private AccountBalanceEvent accountBalanceEvent;
}
