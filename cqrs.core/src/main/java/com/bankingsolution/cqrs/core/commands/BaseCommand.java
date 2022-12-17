package com.bankingsolution.cqrs.core.commands;

import com.bankingsolution.cqrs.core.messages.Message;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class BaseCommand extends Message {
    public BaseCommand(String id){
        super(id);
    }
}
