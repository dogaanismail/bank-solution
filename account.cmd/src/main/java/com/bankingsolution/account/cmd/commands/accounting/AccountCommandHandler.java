package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.dto.AccountBalanceResponse;
import com.bankingsolution.account.cmd.dto.OpenAccountResponse;
import com.bankingsolution.account.cmd.port.CustomerServiceClient;
import com.bankingsolution.common.exceptions.CurrencyNotSupportedException;
import com.bankingsolution.common.exceptions.CustomerNotFoundException;
import com.bankingsolution.common.utils.ObjectMapperUtils;
import com.bankingsolution.common.validation.ValidationHelper;
import com.bankingsolution.cqrs.core.generics.GenericResponse;
import com.bankingsolution.cqrs.core.generics.ResponseModel;
import com.bankingsolution.cqrs.core.generics.ResponseStatus;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountCommandHandler implements IAccountCommandHandler {

    private final EventSourcingHandler<AccountAggregate> eventSourcingHandlers;

    private final CustomerServiceClient customerService;

    public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandlers, CustomerServiceClient customerService) {
        this.eventSourcingHandlers = eventSourcingHandlers;
        this.customerService = customerService;
    }

    @Override
    public ResponseModel handle(OpenAccountCommand command) {

        List<String> currencyList = command.getCurrencies();

        var aggregate = new AccountAggregate();

        validateCurrencies(command.getCurrencies());

        //https://stackoverflow.com/questions/71572351/feign-retryableexception-connection-refused-connection-refused-executing-get
        validateCustomer(command.getCustomerId());

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
            if (ValidationHelper.isCurrencySupported(currencyCode))
                throw new CurrencyNotSupportedException("Invalid currency code : " + currencyCode);
        }
    }

    private void validateCustomer(Long customerId) {
        var customer = customerService.getCustomerById(customerId);

        if (customer == null)
            throw new CustomerNotFoundException("Customer could not be found!");
    }
}
