package com.bankingsolution.account.query.domain;

import com.bankingsolution.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransaction extends BaseEntity {
    private String transactionId;
    private String accountId;
    private int direction;
    private BigDecimal amount;
    private int status;
    private Timestamp transactionTime;
    private String description;
}
