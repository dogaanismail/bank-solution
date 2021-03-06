package com.bankingsolution.cqrs.core.queries;

import com.bankingsolution.cqrs.core.domain.BaseEntity;
import com.bankingsolution.cqrs.core.generics.ResponseModel;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    ResponseModel handle(T query);
}
