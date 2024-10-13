package com.banksolution.account.cmd.domain;

import com.banksolution.account.cmd.commands.accounting.OpenAccountCommand;
import com.banksolution.account.cmd.commands.transaction.TransactionCommand;
import com.banksolution.account.cmd.factory.account.*;
import com.banksolution.common.events.AccountBalanceEvent;
import com.banksolution.common.events.AccountOpenedEvent;
import com.banksolution.common.events.FundsDepositedEvent;
import com.banksolution.common.events.FundsWithDrawnEvent;
import com.banksolution.common.exceptions.AccountAlreadyExists;
import com.banksolution.common.exceptions.AccountBalanceNotFoundException;
import com.banksolution.common.exceptions.InsufficientFundsException;
import com.banksolution.cqrs.core.domain.AggregateRoot;
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
                .map(AccountBalanceFactory::getAccountBalance)
                .toList();
    }

    public void apply(FundsDepositedEvent fundsDepositedEvent) {

        this.id = fundsDepositedEvent.getId();

        AccountBalance accountBalance = findAccountBalance(
                fundsDepositedEvent.getAccountId(),
                fundsDepositedEvent.getCurrencyCode()
        );

        BigDecimal balanceAfterTxn = accountBalance
                .getBalance()
                .add(fundsDepositedEvent.getAmount());

        accountBalance.setBalance(balanceAfterTxn);
        updateAccountBalance(accountBalance);
    }

    public void apply(FundsWithDrawnEvent fundsWithDrawnEvent) {

        this.id = fundsWithDrawnEvent.getId();

        AccountBalance accountBalance = findAccountBalance(
                fundsWithDrawnEvent.getAccountId(),
                fundsWithDrawnEvent.getCurrencyCode()
        );

        BigDecimal balanceAfterTxn = accountBalance.
                getBalance()
                .subtract(fundsWithDrawnEvent.getAmount());

        accountBalance.setBalance(balanceAfterTxn);
        updateAccountBalance(accountBalance);
    }

    public void addAccountBalance(
            String accountId,
            Long customerId,
            String currencyCode) {

        Optional<AccountBalance> accountBalance = findAccountBalance(customerId, currencyCode);

        if (accountBalance.isPresent()) {
            throw new AccountAlreadyExists("Account balance is already exist!");
        }

        AccountBalance balance = new AccountBalance(
                customerId,
                UUID.randomUUID().toString(),
                accountId,
                currencyCode,
                BigDecimal.ZERO,
                BigDecimal.ZERO);

        accountBalances.add(balance);
    }

    public void openAccount(OpenAccountCommand openAccountCommand) {

        AccountOpenedEvent accountOpenedEvent =
                AccountOpenedEventFactory.getAccountOpenedEvent(openAccountCommand);

        List<AccountBalanceEvent> balanceEvents = new ArrayList<>();

        for (AccountBalance accountBalance : accountBalances) {
            AccountBalanceEvent balanceEvent =
                    AccountBalanceEventFactory.getAccountBalanceEvent(
                            accountBalance,
                            openAccountCommand
                    );

            balanceEvents.add(balanceEvent);
        }

        accountOpenedEvent.setAccountBalances(balanceEvents);

        raiseEvent(
                accountOpenedEvent
        );
    }

    public BigDecimal deposit(TransactionCommand transactionCommand) {

        AccountBalance accountBalance = findAccountBalance(
                transactionCommand.getAccountId(),
                transactionCommand.getCurrency()
        );

        BigDecimal balanceAfterTxn = accountBalance.
                getBalance()
                .add(transactionCommand.getAmount());

        raiseEvent(
                FundsDepositedEventFactory.getFundsDepositedEvent(
                        this.id,
                        transactionCommand
                )
        );

        return balanceAfterTxn;
    }

    public BigDecimal withdraw(TransactionCommand transactionCommand) {

        AccountBalance accountBalance = findAccountBalance(
                transactionCommand.getAccountId(),
                transactionCommand.getCurrency()
        );

        BigDecimal balanceAfterTxn = accountBalance
                .getBalance()
                .subtract(transactionCommand.getAmount());

        if (balanceAfterTxn.compareTo(BigDecimal.ZERO) < 0) {
            throw new InsufficientFundsException("Insufficient account balance!");
        }

        raiseEvent(
                FundsWithDrawnEventFactory.getFundsWithDrawnEvent(
                        this.id,
                        transactionCommand
                )
        );

        return balanceAfterTxn;
    }

    private void updateAccountBalance(AccountBalance accountBalance) {

        int index = IntStream.range(0, this.accountBalances.size())
                .filter(i -> this.accountBalances.get(i).getAccountBalanceId().equals(accountBalance.getAccountBalanceId()))
                .findFirst()
                .orElse(-1);

        this.accountBalances.set(index, accountBalance);
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
}
