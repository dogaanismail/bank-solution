package com.bankingsolution.account.cmd.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public record AccountCreateRequest(@Min(1) Long customerId,
                                   @NotNull String country,
                                   @NotNull List<String> currencies) { }
