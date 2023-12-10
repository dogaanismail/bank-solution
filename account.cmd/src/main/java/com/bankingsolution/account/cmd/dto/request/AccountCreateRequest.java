package com.bankingsolution.account.cmd.dto.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class AccountCreateRequest {

    @Min(1)
    private long customerId;

    @NotNull
    private String country;

    @NotNull
    private List<String> currencies;
}
