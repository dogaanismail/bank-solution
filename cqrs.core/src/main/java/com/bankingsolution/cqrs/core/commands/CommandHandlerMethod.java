package com.bankingsolution.cqrs.core.commands;

import com.bankingsolution.cqrs.core.generics.ResponseModel;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    ResponseModel handle(T command);
}
