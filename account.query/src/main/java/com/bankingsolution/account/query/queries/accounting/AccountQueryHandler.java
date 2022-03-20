package com.bankingsolution.account.query.queries.accounting;

import com.bankingsolution.account.query.domain.Account;
import com.bankingsolution.account.query.mappers.AccountMapper;
import com.bankingsolution.cqrs.core.domain.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class AccountQueryHandler implements IAccountQueryHandler {

    @Autowired
    private final AccountMapper accountMapper;

    public AccountQueryHandler(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        Iterable<Account> bankAccounts = accountMapper.getAllAccounts();
        List<BaseEntity> bankAccountList = new ArrayList<>();
        bankAccounts.forEach(bankAccountList::add);
        return bankAccountList;
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountMapper.getAccountById(query.getId());
        if (bankAccount.isEmpty()) {
            return null;
        }

        return Arrays.asList(bankAccount.get());
    }
}
