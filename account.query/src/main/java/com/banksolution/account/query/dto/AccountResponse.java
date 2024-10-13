package com.banksolution.account.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class AccountResponse {
    private String accountId;
    private Long customerId;
    private List<BalanceResponse> accountBalances;

}
