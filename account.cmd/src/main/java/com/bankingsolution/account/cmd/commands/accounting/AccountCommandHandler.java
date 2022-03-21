package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.dto.AccountBalanceResponse;
import com.bankingsolution.account.cmd.dto.OpenAccountResponse;
import com.bankingsolution.common.exceptions.CurrencyNotSupportedException;
import com.bankingsolution.common.utils.ObjectMapperUtils;
import com.bankingsolution.common.validation.ValidationHelper;
import com.bankingsolution.cqrs.core.generics.GenericResponse;
import com.bankingsolution.cqrs.core.generics.ResponseModel;
import com.bankingsolution.cqrs.core.generics.ResponseStatus;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountCommandHandler implements IAccountCommandHandler {

    @Autowired
    private EventSourcingHandler<AccountAggregate> eventSourcingHandlers;

    @Override
    public ResponseModel handle(OpenAccountCommand command) {
        List<String> currencyList = command.getCurrencies();

        var aggregate = new AccountAggregate();

        validateCurrencies(command.getCurrencies());

        for (String currencyCode : currencyList) {
            aggregate.AddAccountBalance(command.getId(), command.getCustomerId(), currencyCode);
        }

        aggregate.openAccount(command);

        eventSourcingHandlers.save(aggregate);

        OpenAccountResponse response =
                OpenAccountResponse.builder()
                        .accountId(aggregate.getId())
                        .customerId(aggregate.getCustomerId())
                        .accountBalances(ObjectMapperUtils.mapAll(aggregate.getAccountBalances(), AccountBalanceResponse.class))
                        .build();

        return GenericResponse.generateResponse(ResponseStatus.SUCCESS, response, "Account has been successfully opened!");
    }

    private void validateCurrencies(List<String> currencies) {
        for (String currencyCode : currencies) {
            if (!ValidationHelper.isCurrencySupported(currencyCode))
                throw new CurrencyNotSupportedException("Invalid currency code : " + currencyCode);
        }
    }
}
