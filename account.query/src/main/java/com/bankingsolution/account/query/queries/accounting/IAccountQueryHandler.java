package com.bankingsolution.account.query.queries.accounting;

import com.bankingsolution.cqrs.core.generics.ResponseModel;

public interface IAccountQueryHandler {
    ResponseModel handle(FindAllAccountsQuery query);

    ResponseModel handle(FindAccountByIdQuery query);
}
