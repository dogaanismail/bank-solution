package com.banksolution.account.cmd.infrastructure.accounting;

import com.banksolution.account.cmd.domain.AccountAggregate;
import com.banksolution.cqrs.core.domain.AggregateRoot;
import com.banksolution.cqrs.core.events.BaseEvent;
import com.banksolution.cqrs.core.handlers.EventSourcingHandler;
import com.banksolution.cqrs.core.infrastructure.EventStore;
import com.banksolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    public AccountEventSourcingHandler(
            @Qualifier("accountEventStore") EventStore eventStore,
            @Qualifier("accountEventProducer") EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregateRoot) {

        eventStore.save(
                aggregateRoot.getId(),
                aggregateRoot.getUncommittedChanges(),
                aggregateRoot.getVersion()
        );
        aggregateRoot.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String id) {

        var accountAggregate = new AccountAggregate();
        var events = Optional.ofNullable(eventStore.getEvents(id));

        events.ifPresent(e -> {
            if (!e.isEmpty()) {
                accountAggregate.replayEvents(e);
                var latestVersion = e.stream().max(Comparator.comparing(BaseEvent::getVersion));
                latestVersion.ifPresent(event -> accountAggregate.setVersion(event.getVersion()));
            }
        });
        return accountAggregate;
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
