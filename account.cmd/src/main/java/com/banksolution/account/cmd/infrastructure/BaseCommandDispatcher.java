package com.banksolution.account.cmd.infrastructure;

import com.banksolution.cqrs.core.commands.BaseCommand;
import com.banksolution.cqrs.core.commands.CommandHandlerMethod;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class BaseCommandDispatcher implements CommandDispatcher {

    private final Map<Class<? extends BaseCommand>, List<CommandHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseCommand> void registerHandler(
            Class<T> type, CommandHandlerMethod<T> handler) {

        var handlers = routes.computeIfAbsent(type, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public ResponseModel send(BaseCommand command) {

        var handlers = routes.get(command.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No command handler was registered!");
        }

        if (handlers.size() > 1) {
            throw new RuntimeException("Cannot send command to more than one handler!");
        }

        return handlers.getFirst().handle(command);
    }
}
