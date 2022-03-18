package com.bankingsolution.account.cmd.commands;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountCommandHandler implements CommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandlers;

    @Override
    public void handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate(command);
        eventSourcingHandlers.save(aggregate);
    }
}
