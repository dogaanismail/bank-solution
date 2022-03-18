package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.OpenAccountCommand;
import com.bankingsolution.common.events.AccountOpenedEvent;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {

    private Long customerId;
    private Boolean active;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Boolean getActive() {
        return active;
    }

    public void setStatus(Boolean active) {
        this.active = active;
    }

    public AccountAggregate(OpenAccountCommand command){
        raiseEvent(
                AccountOpenedEvent.builder()
                        .id(command.getId())
                        .customerId(command.getCustomerId())
                        .initialBalance(BigDecimal.ZERO)
                        .country(command.getCountry())
                        .build());
    }
}
