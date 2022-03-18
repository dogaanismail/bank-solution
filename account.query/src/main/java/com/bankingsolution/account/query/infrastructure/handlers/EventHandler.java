package com.bankingsolution.account.query.infrastructure.handlers;

import com.bankingsolution.common.events.AccountOpenedEvent;

public interface EventHandler {
    void on(AccountOpenedEvent event);
}
