package com.banksolution.cqrs.core.queries;

import com.banksolution.cqrs.core.generics.ResponseModel;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {

    ResponseModel handle(T query);
}
