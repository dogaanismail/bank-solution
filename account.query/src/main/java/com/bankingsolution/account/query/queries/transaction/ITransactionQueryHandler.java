package com.bankingsolution.account.query.queries.transaction;

import com.bankingsolution.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface ITransactionQueryHandler {
    List<BaseEntity> handle(FindAllTransactionsByAccountIdQuery query);
}
