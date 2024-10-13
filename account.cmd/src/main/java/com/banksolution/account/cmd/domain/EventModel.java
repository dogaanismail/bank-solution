package com.banksolution.account.cmd.domain;

import com.banksolution.cqrs.core.events.BaseEvent;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "eventStore")
public class EventModel {
    @MongoId
    private String id;
    private LocalDateTime timestamp;
    private String aggregateIdentifier;
    private String aggregateType;
    private int version;
    private String eventType;
    private BaseEvent eventData;
}