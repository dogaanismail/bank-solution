package com.bankingsolution.account.query.domain;

import com.bankingsolution.cqrs.core.domain.BaseEntity;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {
    private String accountId;
    private Long customerId;
    private String country;
}
