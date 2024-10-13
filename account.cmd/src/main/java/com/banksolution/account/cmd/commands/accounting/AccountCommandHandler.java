package com.banksolution.account.cmd.commands.accounting;

import com.banksolution.account.cmd.client.CustomerServiceClient;
import com.banksolution.account.cmd.domain.AccountAggregate;
import com.banksolution.account.cmd.dto.OpenAccountResponse;
import com.banksolution.account.cmd.factory.account.OpenAccountResponseFactory;
import com.banksolution.common.exceptions.CurrencyNotSupportedException;
import com.banksolution.common.exceptions.CustomerNotFoundException;
import com.banksolution.common.validation.ValidationHelper;
import com.banksolution.cqrs.core.generics.GenericResponse;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.generics.ResponseStatus;
import com.banksolution.cqrs.core.handlers.EventSourcingHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AccountCommandHandler implements IAccountCommandHandler {

    private final static String ACCOUNT_CREATION_SUCCESS_RESPONSE = "Account has been successfully opened!";
    private final EventSourcingHandler<AccountAggregate> eventSourcingHandlers;
    private final CustomerServiceClient customerService;

    @Override
    public ResponseModel handle(OpenAccountCommand openAccountCommand) {
        AccountAggregate accountAggregate = new AccountAggregate();

        validateAndAddAccountBalances(
                accountAggregate,
                openAccountCommand
        );

        accountAggregate.openAccount(openAccountCommand);

        eventSourcingHandlers.save(accountAggregate);

        OpenAccountResponse response = OpenAccountResponseFactory
                .getOpenAccountResponse(accountAggregate);

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                response,
                ACCOUNT_CREATION_SUCCESS_RESPONSE
        );
    }

    private void validateAndAddAccountBalances(
            AccountAggregate accountAggregate,
            OpenAccountCommand openAccountCommand) {

        List<String> currencies = openAccountCommand.getCurrencies();
        validateCurrencies(currencies);
        validateCustomer(openAccountCommand.getCustomerId());

        for (String currency : currencies) {

            accountAggregate.addAccountBalance(
                    openAccountCommand.getId(),
                    openAccountCommand.getCustomerId(),
                    currency
            );
        }
    }

    private void validateCurrencies(
            List<String> currencies) {

        for (String currency : currencies) {

            if (ValidationHelper.isCurrencySupported(currency)) {
                throw new CurrencyNotSupportedException("Invalid currency code : " + currency);
            }
        }
    }

    private void validateCustomer(
            Long customerId) {

        final var customer = customerService.getCustomerById(customerId);

        if (customer == null) {
            throw new CustomerNotFoundException("Customer could not be found!");
        }
    }
}
