package com.banksolution.account.cmd.dto;

import java.math.BigDecimal;

public record AccountBalanceResponse(
        String currencyCode,
        BigDecimal balance) {
}
