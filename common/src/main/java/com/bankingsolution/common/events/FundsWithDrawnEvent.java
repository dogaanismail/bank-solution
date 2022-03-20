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
public class FundsWithDrawnEvent extends BaseEvent implements Serializable {
    private Long customerId;
    private String accountBalanceId;
    private String accountId;
    private String currencyCode;
    private BigDecimal amount;
}
