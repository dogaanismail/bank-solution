package com.bankingsolution.common.events;

import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

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
