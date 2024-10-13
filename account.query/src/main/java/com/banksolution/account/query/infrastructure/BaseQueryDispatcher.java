package com.banksolution.account.query.infrastructure;

import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.infrastructure.QueryDispatcher;
import com.banksolution.cqrs.core.queries.BaseQuery;
import com.banksolution.cqrs.core.queries.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class BaseQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public ResponseModel send(BaseQuery baseQuery) {

        var handlers = routes.get(baseQuery.getClass());

        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No baseQuery handler was registered!");
        }

        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send baseQuery to more than one handler!");
        }

        return handlers.getFirst().handle(baseQuery);
    }
}
