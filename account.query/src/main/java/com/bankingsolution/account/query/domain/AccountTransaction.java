package com.bankingsolution.account.query.domain;

import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.cqrs.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

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
    private String createdAt;
    private String description;
    private String currency;
    private BigDecimal balanceAfterTxn;
}
