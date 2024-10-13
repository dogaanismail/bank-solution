package com.banksolution.account.query.domain;

import com.banksolution.cqrs.core.events.BaseEvent;
import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
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
