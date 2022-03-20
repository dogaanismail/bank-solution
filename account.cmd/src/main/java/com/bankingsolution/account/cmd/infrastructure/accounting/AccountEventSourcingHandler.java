package com.bankingsolution.account.cmd.infrastructure.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import com.bankingsolution.cqrs.core.events.BaseEvent;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import com.bankingsolution.cqrs.core.infrastructure.EventStore;
import com.bankingsolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Qualifier("accountEventStore")
    @Autowired
    private final EventStore eventStore;

    @Qualifier("accountEventProducer")
    @Autowired
    private final EventProducer eventProducer;

    public AccountEventSourcingHandler(@Qualifier("accountEventStore") EventStore eventStore,
                                       @Qualifier("accountEventProducer") EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.save(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(id);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream().map(BaseEvent::getVersion).max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) continue;
            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(event.getClass().getSimpleName(), event);
            }
        }
    }
}
