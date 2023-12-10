package com.bankingsolution.account.cmd.commands.transaction;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.domain.AccountTransaction;
import com.bankingsolution.account.cmd.dto.TransactionCreateResponse;
import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.common.exceptions.*;
import com.bankingsolution.common.validation.ValidationHelper;
import com.bankingsolution.cqrs.core.generics.GenericResponse;
import com.bankingsolution.cqrs.core.generics.ResponseModel;
import com.bankingsolution.cqrs.core.generics.ResponseStatus;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionCommandHandler implements ITransactionCommandHandler {

    private final static List<String> TRANSACTION_DIRECTIONS = List.of(TransactionDirection.IN.name(), TransactionDirection.OUT.name());
    private final EventSourcingHandler<AccountTransaction> transactionEventSourcingHandler;
    private final EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler;

    public TransactionCommandHandler(EventSourcingHandler<AccountTransaction> transactionEventSourcingHandler,
                                     EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler) {
        this.transactionEventSourcingHandler = transactionEventSourcingHandler;
        this.accountAggregateEventSourcingHandler = accountAggregateEventSourcingHandler;
    }

    @Override
    public ResponseModel handle(TransactionCommand command) throws AccountBalanceNotFoundException,
            AccountNotFoundException,
            TransactionAmountException,
            TransactionDescriptonException,
            InvaildDirectionException,
            InsufficientFundsException {

        try {
            validateTransactionCommand(command);

            final var balanceAfterTxn = doTransaction(command);
            command.setBalanceAfterTxn(balanceAfterTxn);

            sendTransactionEvent(command);
            return generateTransactionSuccessResponse(command);
        } catch (Exception exception) {
            markTransactionAsFailed(command);
            throw exception;
        }
    }

    private void validateTransactionCommand(TransactionCommand command) {
        if (accountAggregateEventSourcingHandler.getById(command.getAccountId()) == null) {
            throw new AccountNotFoundException("Account could not be found!");
        }

        if (command.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionAmountException("Transaction amount can not be less than zero!");
        }

        if (command.getDescription().isEmpty()) {
            throw new TransactionDescriptonException("Description can not be missing!");
        }

        if (ValidationHelper.isCurrencySupported(command.getCurrency())) {
            throw new CurrencyNotSupportedException("Currency is not supported!");
        }

        if (!TRANSACTION_DIRECTIONS.contains(command.getDirection())) {
            throw new InvaildDirectionException("Invalid transaction direction!");
        }
    }

    private BigDecimal doTransaction(TransactionCommand command) {
        final var accountAggregate = accountAggregateEventSourcingHandler.getById(command.getAccountId());

        BigDecimal balanceAfterTxn;

        if (command.getDirection().equals(TransactionDirection.IN.toString())) {
            balanceAfterTxn = accountAggregate.deposit(command);
        } else {
            balanceAfterTxn = accountAggregate.withdraw(command);
        }

        accountAggregateEventSourcingHandler.save(accountAggregate);
        return balanceAfterTxn;
    }

    private void sendTransactionEvent(TransactionCommand command) {
        var transactionAggregate = new AccountTransaction();
        transactionAggregate.createTransaction(command);
        transactionEventSourcingHandler.save(transactionAggregate);
    }

    private ResponseModel generateTransactionSuccessResponse(TransactionCommand command) {
        var transactionAggregate = new AccountTransaction();
        transactionAggregate.createTransaction(command);
        final var response = TransactionCreateResponse.builder()
                .accountId(transactionAggregate.getAccountId())
                .transactionId(transactionAggregate.getId())
                .amount(transactionAggregate.getAmount())
                .currencyCode(transactionAggregate.getCurrency())
                .direction(transactionAggregate.getDirection())
                .description(transactionAggregate.getDescription())
                .balanceAfterTxn(transactionAggregate.getBalanceAfterTxn())
                .build();

        return GenericResponse.generateResponse(ResponseStatus.SUCCESS, response, "Transaction has been successfully done!");
    }

    private void markTransactionAsFailed(TransactionCommand command) {
        var failedTransactionAggregate = new AccountTransaction();
        failedTransactionAggregate.crateTransactionFailedData(command);
        transactionEventSourcingHandler.save(failedTransactionAggregate);
    }
}
