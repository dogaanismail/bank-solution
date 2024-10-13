package com.banksolution.cqrs.core.infrastructure;

import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.queries.BaseQuery;
import com.banksolution.cqrs.core.queries.QueryHandlerMethod;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(
            Class<T> type,
            QueryHandlerMethod<T> handler
    );

    ResponseModel send(BaseQuery query);
}
