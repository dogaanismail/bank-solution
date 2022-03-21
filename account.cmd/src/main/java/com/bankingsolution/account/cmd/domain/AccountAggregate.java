package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.common.events.AccountBalanceEvent;
import com.bankingsolution.common.events.AccountOpenedEvent;
import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import com.bankingsolution.common.exceptions.AccountBalanceNotFoundException;
import com.bankingsolution.common.exceptions.InsufficientFundsException;
import com.bankingsolution.common.utils.ObjectMapperUtils;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

public class AccountAggregate extends AggregateRoot {

    private Long customerId;
    private Boolean active;
    private String country;

    protected List<AccountBalance> accountBalances = new ArrayList<>();

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.country = event.getCountry();
        this.customerId = event.getCustomerId();
        this.accountBalances = ObjectMapperUtils.mapAll(event.getAccountBalances(), AccountBalance.class);
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();

        Optional<AccountBalance> accountBalance = findAccountBalance(event.getAccountId(), event.getCurrencyCode());

        BigDecimal balanceAfterTxn = accountBalance.get().getBalance().add(event.getAmount());

        accountBalance.get().setBalance(balanceAfterTxn);
        updateAccountBalance(accountBalance.get());
    }

    public void apply(FundsWithDrawnEvent event) {
        this.id = event.getId();

        Optional<AccountBalance> accountBalance = findAccountBalance(event.getAccountId(), event.getCurrencyCode());

        BigDecimal balanceAfterTxn = accountBalance.get().getBalance().subtract(event.getAmount());

        accountBalance.get().setBalance(balanceAfterTxn);
        updateAccountBalance(accountBalance.get());
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

    public BigDecimal deposit(TransactionCommand command) {

        Optional<AccountBalance> accountBalance = findAccountBalance(command.getAccountId(), command.getCurrency());

        if (accountBalance.isEmpty())
            throw new AccountBalanceNotFoundException("Account balance could not be found!");

        BigDecimal balanceAfterTxn = accountBalance.get().getBalance().add(command.getAmount());

        raiseEvent(
                FundsDepositedEvent.builder().id(this.id)
                        .accountId(command.getAccountId())
                        .currencyCode(command.getCurrency())
                        .amount(command.getAmount())
                        .build()
        );

        return balanceAfterTxn;
    }

    public BigDecimal withdraw(TransactionCommand command) {

        Optional<AccountBalance> accountBalance = findAccountBalance(command.getAccountId(), command.getCurrency());

        if (accountBalance.isEmpty())
            throw new AccountBalanceNotFoundException("Account balance could not be found!");

        BigDecimal balanceAfterTxn = accountBalance.get().getBalance().subtract(command.getAmount());

        if (balanceAfterTxn.compareTo(BigDecimal.ZERO) < 0)
            throw new InsufficientFundsException("Insufficient account balance!");

        raiseEvent(
                FundsWithDrawnEvent.builder().id(this.id)
                        .accountId(command.getAccountId())
                        .currencyCode(command.getCurrency())
                        .amount(command.getAmount())
                        .build()
        );

        return balanceAfterTxn;
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

    public List<AccountBalance> getAccountBalances() {
        return this.accountBalances;
    }

    private void updateAccountBalance(AccountBalance balance) {
        int index = IntStream.range(0, this.accountBalances.size())
                .filter(i -> this.accountBalances.get(i).getAccountBalanceId().equals(balance.getAccountBalanceId()))
                .findFirst()
                .orElse(-1);

        this.accountBalances.set(index, balance);
    }

    private Optional<AccountBalance> findAccountBalance(String accountId, String currencyCode) {
        return this.getAccountBalances()
                .stream().filter(x -> x.getAccountId().equals(accountId) &&
                        x.getCurrencyCode().equals(currencyCode)).findFirst();
    }
}
