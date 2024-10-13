package com.banksolution.account.query.infrastructure.handlers.accounting;

import com.banksolution.common.events.AccountOpenedEvent;

public interface IAccountEventHandler {

    void on(AccountOpenedEvent accountOpenedEvent);
}
