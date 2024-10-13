package com.banksolution.account.cmd.commands.accounting;

import com.banksolution.cqrs.core.generics.ResponseModel;

public interface IAccountCommandHandler {

    ResponseModel handle(OpenAccountCommand command);
}
