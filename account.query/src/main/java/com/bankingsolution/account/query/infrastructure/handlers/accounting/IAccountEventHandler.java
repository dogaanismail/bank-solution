package com.bankingsolution.account.query.infrastructure.handlers.accounting;

import com.bankingsolution.common.events.AccountOpenedEvent;

public interface IAccountEventHandler {
    void on(AccountOpenedEvent event);
}
