package com.banksolution.account.cmd.commands.transaction;

import com.banksolution.common.enums.TransactionDirection;
import com.banksolution.cqrs.core.commands.BaseCommand;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
