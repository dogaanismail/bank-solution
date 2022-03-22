package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.domain.AccountTransaction;
import com.bankingsolution.account.cmd.dto.AccountBalanceResponse;
import com.bankingsolution.account.cmd.dto.OpenAccountResponse;
import com.bankingsolution.account.cmd.port.ICustomerService;
import com.bankingsolution.common.exceptions.CurrencyNotSupportedException;
import com.bankingsolution.common.exceptions.CustomerNotFoundException;
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

    @Autowired
    private ICustomerService customerService;

    @Override
    public ResponseModel handle(OpenAccountCommand command) {

        try {
            List<String> currencyList = command.getCurrencies();

            var aggregate = new AccountAggregate();

            validateCurrencies(command.getCurrencies());

            //TODO: Could not handle sending a reuqest because of refusing
            /*validateCustomer(command.getCustomerId());*/

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
        } catch (Exception exception) {
            throw exception;
        }
    }

    private void validateCurrencies(List<String> currencies) {
        for (String currencyCode : currencies) {
            if (!ValidationHelper.isCurrencySupported(currencyCode))
                throw new CurrencyNotSupportedException("Invalid currency code : " + currencyCode);
        }
    }

    private void validateCustomer(Long customerId) {
        var customer = customerService.getCustomerById(customerId);

        if (customer == null)
            throw new CustomerNotFoundException("Customer could not be found!");
    }
}
