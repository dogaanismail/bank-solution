package com.banksolution.account.cmd.commands.accounting;

import com.banksolution.cqrs.core.commands.BaseCommand;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class OpenAccountCommand extends BaseCommand {
    private long customerId;
    private String country;
    private List<String> currencies;
}
