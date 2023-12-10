package com.bankingsolution.account.query.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
public class BalanceResponse {
    private String currencyCode;
    private BigDecimal balance;
}
