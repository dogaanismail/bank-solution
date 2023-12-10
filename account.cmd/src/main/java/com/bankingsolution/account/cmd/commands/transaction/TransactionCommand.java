package com.bankingsolution.account.cmd.commands.transaction;

import com.bankingsolution.common.enums.TransactionDirection;
import com.bankingsolution.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
public class TransactionCommand extends BaseCommand {

    @NotEmpty
    private String accountId;

    @NotEmpty
    private BigDecimal amount;

    @NotEmpty
    private String currency;

    @NotEmpty
    private TransactionDirection direction;

    @NotEmpty
    private String description;

    private BigDecimal balanceAfterTxn;
}
