package com.banksolution.account.query.queries.accounting;

import com.banksolution.cqrs.core.generics.ResponseModel;

public interface IAccountQueryHandler {
    ResponseModel handle(FindAllAccountsQuery query);

    ResponseModel handle(FindAccountByIdQuery query);
}
