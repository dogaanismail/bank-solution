package com.bankingsolution.cqrs.core.infrastructure;

import com.bankingsolution.cqrs.core.commands.BaseCommand;
import com.bankingsolution.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
