package com.bankingsolution.account.cmd.infrastructure.accounting;

import com.bankingsolution.account.cmd.domain.AccountAggregate;
import com.bankingsolution.cqrs.core.domain.AggregateRoot;
import com.bankingsolution.cqrs.core.events.BaseEvent;
import com.bankingsolution.cqrs.core.handlers.EventSourcingHandler;
import com.bankingsolution.cqrs.core.infrastructure.EventStore;
import com.bankingsolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
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
        var events = Optional.ofNullable(eventStore.getEvents(id));

        events.ifPresent(e -> {
            if (!e.isEmpty()) {
                aggregate.replayEvents(e);
                var latestVersion = e.stream().max(Comparator.comparing(BaseEvent::getVersion));
                latestVersion.ifPresent(event -> aggregate.setVersion(event.getVersion()));
            }
        });
        return aggregate;
    }

    @Override
    public void republishEvents() {
        eventStore.getAggregateIds().stream()
                .map(this::getById)
                .filter(aggregate -> aggregate != null && aggregate.getActive())
                .forEach(aggregate ->
                        eventStore.getEvents(aggregate.getId()).forEach(
                                event -> eventProducer.produce(event.getClass().getSimpleName(), event)
                        )
                );
    }
}
