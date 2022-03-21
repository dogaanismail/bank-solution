package com.bankingsolution.common.events;

import com.bankingsolution.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionCreatedEvent extends BaseEvent implements Serializable {
    private String accountId;
    private String direction;
    private BigDecimal amount;
    private String status;
    private String currencyCode;
    private String description;
    private Timestamp transactionTime;
    private BigDecimal balanceAfterTxn;
}
