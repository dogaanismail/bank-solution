package com.bankingsolution.account.query.queries;

import com.bankingsolution.cqrs.core.domain.BaseEntity;

import java.util.List;

public interface IAccountQueryHandler {
    List<BaseEntity> handle(FindAllAccountsQuery query);

    List<BaseEntity> handle(FindAccountByIdQuery query);
}
