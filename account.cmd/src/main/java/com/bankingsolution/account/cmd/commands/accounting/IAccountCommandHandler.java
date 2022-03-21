package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.cqrs.core.generics.ResponseModel;

public interface IAccountCommandHandler {
    ResponseModel handle(OpenAccountCommand command);
}
