package com.banksolution.account.cmd.factory;

import com.banksolution.account.cmd.domain.AccountAggregate;
import com.banksolution.account.cmd.domain.EventModel;
import com.banksolution.cqrs.core.events.BaseEvent;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class EventModelFactory {

    public static EventModel getEventModel(
            BaseEvent baseEvent,
            String aggregateId,
            int version) {

        return EventModel.builder()
                .timestamp(LocalDateTime.now())
                .aggregateIdentifier(aggregateId)
                .aggregateType(AccountAggregate.class.getTypeName())
                .version(version)
                .eventType(baseEvent.getClass().getTypeName())
                .eventData(baseEvent)
                .build();
    }
}
