package com.banksolution.cqrs.core.commands;

import com.banksolution.cqrs.core.generics.ResponseModel;

@FunctionalInterface
public interface CommandHandlerMethod<T extends BaseCommand> {
    ResponseModel handle(T command);
}
