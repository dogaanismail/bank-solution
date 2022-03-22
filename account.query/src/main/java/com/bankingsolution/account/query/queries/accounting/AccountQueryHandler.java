package com.bankingsolution.account.query.queries.accounting;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.dto.AccountResponse;
import com.bankingsolution.account.query.dto.BalanceResponse;
import com.bankingsolution.account.query.mappers.AccountBalanceMapper;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.common.utils.ObjectMapperUtils;
import com.bankingsolution.cqrs.core.generics.GenericResponse;
import com.bankingsolution.cqrs.core.generics.ResponseModel;
import com.bankingsolution.cqrs.core.generics.ResponseStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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
        List<Account> bankAccounts = accountMapper.getAllAccounts();

        if (bankAccounts.size() == 0 && bankAccounts.isEmpty())
            GenericResponse.generateResponse(ResponseStatus.ERROR, null, "There is no any account!");

        return GenericResponse.generateResponse(ResponseStatus.SUCCESS, bankAccounts);
    }

    @Override
    public ResponseModel handle(FindAccountByIdQuery query) {
        var bankAccount = accountMapper.getAccountById(query.getId());

        if (bankAccount == null)
            return GenericResponse.generateResponse(ResponseStatus.ERROR, null, "Invalid bank account!");

        var accountBalance = accountBalanceMapper.getBalancesByAccountId(query.getId());

        AccountResponse response = ObjectMapperUtils.map(bankAccount, AccountResponse.class);
        List<BalanceResponse> balances = ObjectMapperUtils.mapAll(accountBalance, BalanceResponse.class);

        response.setAccountBalances(balances);
        return GenericResponse.generateResponse(ResponseStatus.SUCCESS, response);
    }
}
