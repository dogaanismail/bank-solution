package com.bankingsolution.account.query.queries.transaction;

import com.bankingsolution.account.query.domain.AccountTransaction;
import com.bankingsolution.account.query.mappers.TransactionMapper;
import com.bankingsolution.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionQueryHandler implements ITransactionQueryHandler {

    @Autowired
    private final TransactionMapper transactionMapper;

    public TransactionQueryHandler(TransactionMapper transactionMapper) {
        this.transactionMapper = transactionMapper;
    }

    @Override
    public List<BaseEntity> handle(FindAllTransactionsByAccountIdQuery query) {
        Iterable<AccountTransaction> transactions = transactionMapper.getTransactionsByAccountId(query.getId());
        List<BaseEntity> transactionList = new ArrayList<>();
        transactions.forEach(transactionList::add);
        return transactionList;
    }
}
