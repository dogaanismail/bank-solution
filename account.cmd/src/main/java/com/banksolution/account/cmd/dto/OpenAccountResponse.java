package com.banksolution.account.cmd.dto;

import java.util.List;


public record OpenAccountResponse(
        String accountId,
        Long customerId,
        List<AccountBalanceResponse> accountBalances) {
}

