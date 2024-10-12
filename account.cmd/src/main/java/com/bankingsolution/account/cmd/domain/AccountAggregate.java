package com.bankingsolution.account.cmd.domain;

import com.bankingsolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.bankingsolution.account.cmd.commands.transaction.TransactionCommand;
import com.bankingsolution.common.events.AccountBalanceEvent;
import com.bankingsolution.common.events.AccountOpenedEvent;
import com.bankingsolution.common.events.FundsDepositedEvent;
import com.bankingsolution.common.events.FundsWithDrawnEvent;
import com.bankingsolution.common.exceptions.AccountAlreadyExists;
import com.bankingsolution.common.exceptions.AccountBalanceNotFoundException;
import com.bankingsolution.common.exceptions.InsufficientFundsException;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Getter
public class AccountAggregate extends AggregateRoot {

    private Long customerId;
    private Boolean active;
    private String country;
    private Instant createdAt;

    protected List<AccountBalance> accountBalances = new ArrayList<>();

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.country = event.getCountry();
        this.customerId = event.getCustomerId();
        this.createdAt = Instant.now();
        this.accountBalances = event.getAccountBalances()
                .stream()
                .map(this::mapBalance)
                .toList();
    }

    public void apply(FundsDepositedEvent event) {
        this.id = event.getId();

        var accountBalance = findAccountBalance(
                event.getAccountId(),
                event.getCurrencyCode()
        );

        final var balanceAfterTxn = accountBalance.getBalance().add(event.getAmount());

        accountBalance.setBalance(balanceAfterTxn);
        updateAccountBalance(accountBalance);
    }

    public void apply(FundsWithDrawnEvent event) {

        this.id = event.getId();

        var accountBalance = findAccountBalance(event.getAccountId(), event.getCurrencyCode());

        final var balanceAfterTxn = accountBalance.getBalance().subtract(event.getAmount());

        accountBalance.setBalance(balanceAfterTxn);
        updateAccountBalance(accountBalance);
    }

    public void addAccountBalance(
            String accountId,
            Long customerId,
            String currencyCode) {

        final var accountBalance = findAccountBalance(customerId, currencyCode);

        if (accountBalance.isPresent()) {
            throw new AccountAlreadyExists("Account balance is already exist!");
        }

        final var balance = new AccountBalance(
                UUID.randomUUID().toString(),
                customerId,
                currencyCode,
                BigDecimal.ZERO,
                BigDecimal.ZERO,
                accountId);

        accountBalances.add(balance);
    }

    public void openAccount(OpenAccountCommand command) {
        var accountOpenedEvent = AccountOpenedEvent.builder()
                .id(command.getId())
                .accountId(command.getId())
                .customerId(command.getCustomerId())
                .country(command.getCountry())
                .build();

        List<AccountBalanceEvent> balanceEvents = new ArrayList<>();

        for (final var balance : accountBalances) {
            final var balanceEvent =
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
        var accountBalance = findAccountBalance(command.getAccountId(), command.getCurrency());

        final var balanceAfterTxn = accountBalance.getBalance().add(command.getAmount());

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
        var accountBalance = findAccountBalance(command.getAccountId(), command.getCurrency());

        final var balanceAfterTxn = accountBalance.getBalance().subtract(command.getAmount());

        if (balanceAfterTxn.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient account balance!");
        }

        raiseEvent(
                FundsWithDrawnEvent.builder().id(this.id)
                        .accountId(command.getAccountId())
                        .currencyCode(command.getCurrency())
                        .amount(command.getAmount())
                        .build()
        );

        return balanceAfterTxn;
    }

    private void updateAccountBalance(AccountBalance balance) {

        final var index = IntStream.range(0, this.accountBalances.size())
                .filter(i -> this.accountBalances.get(i).getAccountBalanceId().equals(balance.getAccountBalanceId()))
                .findFirst()
                .orElse(-1);

        this.accountBalances.set(index, balance);
    }

    private AccountBalance findAccountBalance(
            String accountId,
            String currencyCode) {

        return this.getAccountBalances()
                .stream()
                .filter(x ->
                        x.getAccountId().equals(accountId) &&
                                x.getCurrencyCode().equals(currencyCode))
                .findFirst()
                .orElseThrow(() -> new AccountBalanceNotFoundException("Account balance could not be found!"));
    }

    private Optional<AccountBalance> findAccountBalance(
            Long customerId,
            String currencyCode) {

        return this.getAccountBalances()
                .stream()
                .filter(x ->
                        x.getCustomerId().equals(customerId) &&
                                x.getCurrencyCode().equals(currencyCode))
                .findFirst();
    }

    private AccountBalance mapBalance(
            AccountBalanceEvent balanceEvent) {

        return new AccountBalance(
                balanceEvent.getCustomerId(),
                balanceEvent.getAccountBalanceId(),
                balanceEvent.getAccountId(),
                balanceEvent.getCurrencyCode(),
                balanceEvent.getBalance(),
                balanceEvent.getAvailableBalance());
    }
}
