package com.banksolution.account.query.factory.account;

import com.banksolution.account.query.domain.Account;
import com.banksolution.account.query.domain.AccountBalance;
import com.banksolution.account.query.dto.AccountResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AccountResponseFactory {

    public static AccountResponse getAccountResponse(
            Account account,
            List<AccountBalance> accountBalances) {

        return new AccountResponse(
                account.getAccountId(),
                account.getCustomerId(),
                accountBalances
                        .stream()
                        .map(BalanceResponseFactory::getBalanceResponse)
                        .toList()
        );
    }
}
