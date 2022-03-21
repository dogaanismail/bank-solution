package com.bankingsolution.account.cmd.commands.transaction;

import com.bankingsolution.cqrs.core.commands.BaseCommand;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
public class TransactionCommand extends BaseCommand {

    @NotEmpty
    private String accountId;

    @NotEmpty
    private BigDecimal amount;

    @NotEmpty
    private String currency;

    @NotEmpty
    private String direction;

    @NotEmpty
    private String description;

    private BigDecimal balanceAfterTxn;
}
