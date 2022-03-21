package com.bankingsolution.account.cmd.dto;

import com.bankingsolution.account.cmd.domain.AccountBalance;
import com.bankingsolution.common.dto.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OpenAccountResponse {

    private String accountId;
    private Long customerId;
    private List<AccountBalanceResponse> accountBalances;
}

