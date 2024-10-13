package com.banksolution.account.query.queries.accounting;

import com.banksolution.cqrs.core.generics.ResponseModel;

public interface IAccountQueryHandler {

    ResponseModel handle(FindAllAccountsQuery findAllAccountsQuery);

    ResponseModel handle(FindAccountByIdQuery findAccountByIdQuery);
}
