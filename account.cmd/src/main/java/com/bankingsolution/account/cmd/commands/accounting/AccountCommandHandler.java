package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.account.cmd.dto.AccountBalanceResponse;
import com.bankingsolution.account.cmd.dto.OpenAccountResponse;
import com.bankingsolution.account.cmd.client.CustomerServiceClient;
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

    public AccountCommandHandler(EventSourcingHandler<AccountAggregate> eventSourcingHandlers,
                                 CustomerServiceClient customerService) {
        this.eventSourcingHandlers = eventSourcingHandlers;
        this.customerService = customerService;
    }

    @Override
    public ResponseModel handle(OpenAccountCommand command) {
        var aggregate = new AccountAggregate();

        validateAndAddAccountBalances(aggregate, command);

        aggregate.openAccount(command);

        eventSourcingHandlers.save(aggregate);

        final var response = createOpenAccountResponse(aggregate);

        return GenericResponse.generateResponse(ResponseStatus.SUCCESS, response, "Account has been successfully opened!");
    }

    private void validateAndAddAccountBalances(AccountAggregate aggregate, OpenAccountCommand command) {
        final var currencyList = command.getCurrencies();
        validateCurrencies(currencyList);
        validateCustomer(command.getCustomerId());

        for (final var currencyCode : currencyList) {
            aggregate.addAccountBalance(command.getId(), command.getCustomerId(), currencyCode);
        }
    }

    private OpenAccountResponse createOpenAccountResponse(AccountAggregate aggregate) {
        return OpenAccountResponse.builder()
                .accountId(aggregate.getId())
                .customerId(aggregate.getCustomerId())
                .accountBalances(ObjectMapperUtils.mapAll(aggregate.getAccountBalances(), AccountBalanceResponse.class))
                .build();
    }

    private void validateCurrencies(List<String> currencies) {
        for (final var currencyCode : currencies) {
            if (ValidationHelper.isCurrencySupported(currencyCode)) {
                throw new CurrencyNotSupportedException("Invalid currency code : " + currencyCode);
            }
        }
    }

    private void validateCustomer(Long customerId) {
        final var customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer could not be found!");
        }
    }
}
