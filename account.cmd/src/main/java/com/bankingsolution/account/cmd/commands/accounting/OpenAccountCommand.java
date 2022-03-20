package com.bankingsolution.account.cmd.commands.accounting;

import com.bankingsolution.cqrs.core.commands.BaseCommand;
import lombok.Data;
import java.util.List;

@Data
public class OpenAccountCommand extends BaseCommand {
    private long customerId;
    private String country;
    private List<String> currencies;
}
