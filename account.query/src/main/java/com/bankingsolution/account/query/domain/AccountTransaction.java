package com.bankingsolution.account.query.domain;

import com.bankingsolution.cqrs.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransaction extends BaseEntity {
    private String transactionId;
    private String accountId;
    private String direction;
    private BigDecimal amount;
    private String status;
    private Timestamp transactionTime;
    private String description;
    private String currency;
    private BigDecimal balanceAfterTxn;
}
