package com.bankingsolution.account.query.queries.transaction;

import com.bankingsolution.cqrs.core.generics.ResponseModel;


public interface ITransactionQueryHandler {
    ResponseModel handle(FindAllTransactionsByAccountIdQuery query);
}
