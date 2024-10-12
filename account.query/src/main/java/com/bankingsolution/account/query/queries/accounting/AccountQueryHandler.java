package com.bankingsolution.account.query.queries.accounting;

import com.bankingsolution.account.query.domain.AccountBalance;
import com.bankingsolution.account.query.dto.AccountResponse;
import com.bankingsolution.account.query.dto.BalanceResponse;
import com.bankingsolution.account.query.mappers.AccountBalanceMapper;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.cqrs.core.generics.GenericResponse;
import com.bankingsolution.cqrs.core.generics.ResponseModel;
import com.bankingsolution.cqrs.core.generics.ResponseStatus;
import org.springframework.stereotype.Service;

@Service
public class AccountQueryHandler implements IAccountQueryHandler {

    private final AccountMapper accountMapper;
    private final AccountBalanceMapper accountBalanceMapper;

    public AccountQueryHandler(AccountMapper accountMapper,
                               AccountBalanceMapper accountBalanceMapper) {
        this.accountMapper = accountMapper;
        this.accountBalanceMapper = accountBalanceMapper;
    }

    @Override
    public ResponseModel handle(FindAllAccountsQuery query) {

        final var bankAccounts = accountMapper.getAllAccounts();

        if (bankAccounts.isEmpty()) {
            return GenericResponse.generateResponse(
                    ResponseStatus.ERROR,
                    null,
                    "There is no any account!"
            );
        }

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                bankAccounts
        );
    }

    @Override
    public ResponseModel handle(FindAccountByIdQuery query) {

        final var bankAccount = accountMapper.getAccountById(query.getId());

        if (bankAccount == null) {
            return GenericResponse.generateResponse(
                    ResponseStatus.ERROR,
                    null,
                    "Invalid bank account!"
            );
        }

        final var balances = accountBalanceMapper.getBalancesByAccountId(query.getId());

        final var response = new AccountResponse(
                bankAccount.getAccountId(),
                bankAccount.getCustomerId(),
                balances.
                        stream()
                        .map(this::mapBalance)
                        .toList()
        );

        return GenericResponse.generateResponse(
                ResponseStatus.SUCCESS,
                response
        );
    }

    private BalanceResponse mapBalance(AccountBalance balance) {

        return new BalanceResponse(
                balance.getCurrencyCode(),
                balance.getAvailableBalance()
        );
    }
}
