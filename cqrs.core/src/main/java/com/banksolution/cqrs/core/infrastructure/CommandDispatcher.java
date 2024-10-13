package com.banksolution.cqrs.core.infrastructure;

import com.banksolution.cqrs.core.commands.BaseCommand;
import com.banksolution.cqrs.core.commands.CommandHandlerMethod;
import com.banksolution.cqrs.core.generics.ResponseModel;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(
            Class<T> type,
            CommandHandlerMethod<T> handler
    );

    ResponseModel send(BaseCommand command);
}
