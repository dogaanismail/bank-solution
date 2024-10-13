package com.banksolution.account.query.queries.accounting;

import com.banksolution.account.query.domain.Account;
import com.banksolution.account.query.domain.AccountBalance;
import com.banksolution.account.query.dto.AccountResponse;
import com.banksolution.account.query.factory.account.AccountResponseFactory;
import com.banksolution.account.query.mappers.AccountBalanceMapper;
import com.banksolution.account.query.mappers.AccountMapper;
import com.banksolution.cqrs.core.generics.GenericResponse;
import com.banksolution.cqrs.core.generics.ResponseModel;
import com.banksolution.cqrs.core.generics.ResponseStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements IAccountQueryHandler {

    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;

    @Override
    public ResponseModel handle(FindAllAccountsQuery findAllAccountsQuery) {

        List<Account> accounts = accountMapper.getAllAccounts();

        if (accounts.isEmpty()) {
            return GenericResponse.generateResponse(
                    ResponseStatus.ERROR,
                    null,
                    "There is no any account!"
            );
        }

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                accounts
        );
    }

    @Override
    public ResponseModel handle(FindAccountByIdQuery findAccountByIdQuery) {

        Account account = accountMapper.getAccountById(findAccountByIdQuery.getId());

        if (account == null) {
            return GenericResponse.generateResponse(
                    ResponseStatus.ERROR,
                    null,
                    "Invalid bank account!"
            );
        }

        List<AccountBalance> accountBalances = accountBalanceMapper
                .getBalancesByAccountId(findAccountByIdQuery.getId());

        AccountResponse accountResponse = AccountResponseFactory
                .getAccountResponse(
                        account,
                        accountBalances
                );

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                accountResponse
        );
    }
}
