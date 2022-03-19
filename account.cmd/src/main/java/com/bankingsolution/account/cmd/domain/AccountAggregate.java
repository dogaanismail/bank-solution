package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.OpenAccountCommand;
import com.bankingsolution.common.events.AccountBalanceEvent;
import com.bankingsolution.common.events.AccountOpenedEvent;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        AccountBalance balance = new AccountBalance(customerId, currencyCode, BigDecimal.ZERO, BigDecimal.ZERO, accountId);
        accountBalances.add(balance);
    }

    public void openAccount(OpenAccountCommand command) {
        AccountOpenedEvent accountOpenedEvent = new AccountOpenedEvent();
        accountOpenedEvent.setId(command.getId());
        accountOpenedEvent.setAccountId(command.getId());
        accountOpenedEvent.setCustomerId(command.getCustomerId());
        accountOpenedEvent.setCountry(command.getCountry());

        List<AccountBalanceEvent> balanceEvents = new ArrayList<AccountBalanceEvent>();

        for (AccountBalance balance : accountBalances) {
            AccountBalanceEvent balanceEvent = new AccountBalanceEvent(balance.getCustomerId(), balance.getAccountBalanceId(),
                    balance.getAccountId(), balance.getCurrencyCode(),
                    balance.getBalance(), balance.getAvailableBalance());
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
