package com.bankingsolution.account.query.queries.transaction;

import com.bankingsolution.account.query.domain.AccountTransaction;
import com.bankingsolution.account.query.mappers.TransactionMapper;
import com.bankingsolution.common.utils.ObjectMapperUtils;
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
        List<AccountTransaction> transactions = transactionMapper.getTransactionsByAccountId(query.getId());

        if (transactions.size() == 0 && transactions.isEmpty())
            return null;

        return ObjectMapperUtils.mapAll(transactions, BaseEntity.class);
    }
}
