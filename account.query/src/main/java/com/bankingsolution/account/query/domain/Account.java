package com.bankingsolution.account.query.domain;

import com.bankingsolution.cqrs.core.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {
    private String accountId;
    private Long customerId;
}
