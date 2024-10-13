package com.banksolution.account.query.queries.transaction;

import com.banksolution.account.query.domain.AccountTransaction;
import com.banksolution.account.query.mappers.TransactionMapper;
import com.banksolution.cqrs.core.generics.GenericResponse;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.generics.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionQueryHandler implements ITransactionQueryHandler {

    private final TransactionMapper transactionMapper;

    public TransactionQueryHandler(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public ResponseModel handle(FindAllTransactionsByAccountIdQuery query) {

        List<AccountTransaction> transactions = transactionMapper
                .getTransactionsByAccountId(query.getId());

        if (transactions.isEmpty()) {
            return GenericResponse.generateResponse(
                    ResponseStatus.ERROR,
                    null,
                    "Invalid account!"
            );
        }

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                transactions
        );
    }
}
