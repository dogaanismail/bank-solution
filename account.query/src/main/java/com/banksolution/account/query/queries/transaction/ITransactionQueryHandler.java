package com.banksolution.account.query.queries.transaction;

import com.banksolution.cqrs.core.generics.ResponseModel;


public interface ITransactionQueryHandler {
    ResponseModel handle(FindAllTransactionsByAccountIdQuery query);
}
