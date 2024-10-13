package com.banksolution.account.cmd.commands.transaction;

import com.banksolution.account.cmd.domain.AccountAggregate;
import com.banksolution.account.cmd.domain.AccountTransaction;
import com.banksolution.account.cmd.dto.TransactionCreateResponse;
import com.banksolution.account.cmd.factory.transaction.TransactionCreateResponseFactory;
import com.banksolution.common.enums.TransactionDirection;
import com.banksolution.common.exceptions.*;
import com.banksolution.common.validation.ValidationHelper;
import com.banksolution.cqrs.core.generics.GenericResponse;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.generics.ResponseStatus;
import com.banksolution.cqrs.core.handlers.EventSourcingHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class TransactionCommandHandler implements ITransactionCommandHandler {

    private final static String TRANSACTION_SUCCESS_RESPONSE = "Transaction has been successfully done!";
    private final static List<TransactionDirection> TRANSACTION_DIRECTIONS = List.of(
            TransactionDirection.IN,
            TransactionDirection.OUT
    );
    private final EventSourcingHandler<AccountTransaction> transactionEventSourcingHandler;
    private final EventSourcingHandler<AccountAggregate> accountAggregateEventSourcingHandler;

    @Override
    public ResponseModel handle(TransactionCommand transactionCommand) throws AccountBalanceNotFoundException,
            AccountNotFoundException,
            TransactionAmountException,
            TransactionDescriptonException,
            InvaildDirectionException,
            InsufficientFundsException {

        try {
            validateTransactionCommand(transactionCommand);

            BigDecimal balanceAfterTxn = doTransaction(transactionCommand);
            transactionCommand.setBalanceAfterTxn(balanceAfterTxn);

            return createTransaction(transactionCommand);
        } catch (Exception exception) {
            markTransactionAsFailed(transactionCommand);
            throw exception;
        }
    }

    private BigDecimal doTransaction(TransactionCommand transactionCommand) {
        AccountAggregate accountAggregate = accountAggregateEventSourcingHandler.getById(transactionCommand.getAccountId());

        BigDecimal balanceAfterTxn;

        if (transactionCommand.getDirection() == TransactionDirection.IN) {
            balanceAfterTxn = accountAggregate.deposit(transactionCommand);
        } else {
            balanceAfterTxn = accountAggregate.withdraw(transactionCommand);
        }

        accountAggregateEventSourcingHandler.save(accountAggregate);
        return balanceAfterTxn;
    }


    private ResponseModel createTransaction(TransactionCommand transactionCommand) {
        AccountTransaction accountTransaction = new AccountTransaction();

        accountTransaction.createTransaction(transactionCommand);
        transactionEventSourcingHandler.save(accountTransaction);

        TransactionCreateResponse transactionCreateResponse = TransactionCreateResponseFactory
                .getTransactionCreateResponse(accountTransaction);

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                transactionCreateResponse,
                TRANSACTION_SUCCESS_RESPONSE
        );
    }

    private void markTransactionAsFailed(TransactionCommand transactionCommand) {

        AccountTransaction accountTransaction = new AccountTransaction();
        accountTransaction.createFailedTransaction(transactionCommand);
        transactionEventSourcingHandler.save(accountTransaction);
    }

    private void validateTransactionCommand(
            TransactionCommand transactionCommand) {

        if (accountAggregateEventSourcingHandler.getById(transactionCommand.getAccountId()) == null) {
            throw new AccountNotFoundException("Account could not be found!");
        }

        if (transactionCommand.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new TransactionAmountException("Transaction amount can not be less than zero!");
        }

        if (transactionCommand.getDescription().isEmpty()) {
            throw new TransactionDescriptonException("Description can not be missing!");
        }

        if (ValidationHelper.isCurrencySupported(transactionCommand.getCurrency())) {
            throw new CurrencyNotSupportedException("Currency is not supported!");
        }

        if (!TRANSACTION_DIRECTIONS.contains(transactionCommand.getDirection())) {
            throw new InvaildDirectionException("Invalid transaction direction!");
        }
    }
}
