package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.bankingsolution.common.events.AccountBalanceEvent;
import com.bankingsolution.common.events.AccountOpenedEvent;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AccountAggregate extends AggregateRoot {
    private Long customerId;
    private Boolean active;
    private String country;

    private List<AccountBalance> accountBalances;

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.country = event.getCountry();
        this.customerId = event.getCustomerId();
    }

    public void AddAccountBalance(String accountId, Long customerId, String currencyCode) {
        AccountBalance balance = new AccountBalance(UUID.randomUUID().toString(), customerId, currencyCode,
                BigDecimal.ZERO, BigDecimal.ZERO, accountId);
        accountBalances.add(balance);
    }

    public void openAccount(OpenAccountCommand command) {
        AccountOpenedEvent accountOpenedEvent =
                AccountOpenedEvent.builder()
                        .id(command.getId())
                        .accountId(command.getId())
                        .customerId(command.getCustomerId())
                        .country(command.getCountry())
                        .build();

        List<AccountBalanceEvent> balanceEvents = new ArrayList<AccountBalanceEvent>();

        for (AccountBalance balance : accountBalances) {
            AccountBalanceEvent balanceEvent =
                    AccountBalanceEvent.builder()
                            .customerId(balance.getCustomerId())
                            .accountBalanceId(balance.getAccountBalanceId())
                            .accountId(command.getId())
                            .currencyCode(balance.getCurrencyCode())
                            .balance(balance.getBalance())
                            .availableBalance(balance.getAvailableBalance())
                            .build();

            balanceEvents.add(balanceEvent);
        }

        accountOpenedEvent.setAccountBalances(balanceEvents);

        raiseEvent(
                accountOpenedEvent
        );
    }

    public AccountAggregate() {
        accountBalances = new ArrayList<AccountBalance>();
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

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
}
