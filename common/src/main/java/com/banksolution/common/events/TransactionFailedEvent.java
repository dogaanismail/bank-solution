package com.banksolution.common.events;

import com.banksolution.common.enums.TransactionDirection;
import com.banksolution.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TransactionFailedEvent extends BaseEvent implements Serializable {
    private String accountId;
    private TransactionDirection direction;
    private BigDecimal amount;
    private String status;
    private String currencyCode;
    private String description;
    private String createdAt;
}
