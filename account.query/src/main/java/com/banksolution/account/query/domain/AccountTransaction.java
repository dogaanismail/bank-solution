package com.banksolution.account.query.domain;

import com.banksolution.cqrs.core.domain.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

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
