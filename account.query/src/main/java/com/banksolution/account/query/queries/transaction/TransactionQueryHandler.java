package com.banksolution.account.query.queries.transaction;

import com.banksolution.account.query.domain.AccountTransaction;
import com.banksolution.account.query.mappers.TransactionMapper;
import com.banksolution.cqrs.core.generics.GenericResponse;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.generics.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionQueryHandler implements ITransactionQueryHandler {

    private final TransactionMapper transactionMapper;

    @Override
    public ResponseModel handle(
            FindAllTransactionsByAccountIdQuery findAllTransactionsByAccountIdQuery) {

        List<AccountTransaction> transactions = transactionMapper
                .getTransactionsByAccountId(findAllTransactionsByAccountIdQuery.getId());

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
