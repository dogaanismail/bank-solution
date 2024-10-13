package com.banksolution.account.cmd.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
