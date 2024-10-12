package com.bankingsolution.cqrs.core.queries;

import com.bankingsolution.cqrs.core.generics.ResponseModel;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {

    ResponseModel handle(T query);
}
