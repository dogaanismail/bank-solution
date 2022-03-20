package com.bankingsolution.account.query.domain;

import com.bankingsolution.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountBalance extends BaseEvent {
    private Long customerId;
    private String accountBalanceId;
    private String accountId;
    private String currencyCode;
    private BigDecimal balance;
    private BigDecimal availableBalance;
}
