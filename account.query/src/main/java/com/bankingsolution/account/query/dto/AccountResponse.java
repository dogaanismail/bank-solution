package com.bankingsolution.account.query.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AccountResponse {
    private String accountId;
    private Long customerId;
    private List<BalanceResponse> accountBalances;

}
