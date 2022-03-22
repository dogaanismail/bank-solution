package com.bankingsolution.account.query.queries.accounting;

import com.bankingsolution.cqrs.core.domain.BaseEntity;
import com.bankingsolution.cqrs.core.generics.ResponseModel;

import java.util.List;

public interface IAccountQueryHandler {
    ResponseModel handle(FindAllAccountsQuery query);

    ResponseModel handle(FindAccountByIdQuery query);
}
