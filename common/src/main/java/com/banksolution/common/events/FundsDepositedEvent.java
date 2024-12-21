package com.banksolution.common.events;

import com.banksolution.cqrs.core.events.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class FundsDepositedEvent extends BaseEvent implements Serializable {
    private String accountId;
    private String currencyCode;
    private BigDecimal amount;
}
