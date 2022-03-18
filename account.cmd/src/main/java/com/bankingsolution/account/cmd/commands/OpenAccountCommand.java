package com.bankingsolution.account.cmd.commands;

import com.bankingsolution.cqrs.core.commands.BaseCommand;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class OpenAccountCommand extends BaseCommand {
    private long customerId;
    private String country;
}
