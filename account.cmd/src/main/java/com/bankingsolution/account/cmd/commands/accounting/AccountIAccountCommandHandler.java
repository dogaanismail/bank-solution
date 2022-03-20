package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountIAccountCommandHandler implements IAccountCommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandlers;

    @Override
    public void handle(OpenAccountCommand command) {
        List<String> currencyList = command.getCurrencies();

        var aggregate = new AccountAggregate();

        for (String code : currencyList) {
            aggregate.AddAccountBalance(command.getId(), command.getCustomerId(), code);
        }

        aggregate.openAccount(command);

        eventSourcingHandlers.save(aggregate);
    }
}
