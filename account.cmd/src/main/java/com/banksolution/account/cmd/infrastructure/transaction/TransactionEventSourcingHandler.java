package com.banksolution.account.cmd.infrastructure.transaction;

import com.banksolution.account.cmd.domain.AccountTransaction;
import com.banksolution.common.exceptions.TransactionNotFoundException;
import com.banksolution.cqrs.core.domain.AggregateRoot;
import com.banksolution.cqrs.core.events.BaseEvent;
import com.banksolution.cqrs.core.handlers.EventSourcingHandler;
import com.banksolution.cqrs.core.infrastructure.EventStore;
import com.banksolution.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class TransactionEventSourcingHandler implements EventSourcingHandler<AccountTransaction> {

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    public TransactionEventSourcingHandler(
            @Qualifier("transactionEventStore") EventStore eventStore,
            @Qualifier("transactionEventProducer") EventProducer eventProducer) {
        this.eventStore = eventStore;
        this.eventProducer = eventProducer;
    }

    @Override
    public void save(AggregateRoot aggregate) {

        eventStore.save(aggregate.getId(),
                aggregate.getUncommittedChanges(),
                aggregate.getVersion()
        );
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountTransaction getById(String id) {

        AccountTransaction aggregate = new AccountTransaction();
        List<BaseEvent> events = eventStore.getEvents(id);

        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);

            Integer latestVersion = events.
                    stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder())
                    .orElseThrow(() -> new TransactionNotFoundException("Transaction could not be found!"));

            aggregate.setVersion(latestVersion);
        }

        return aggregate;
    }

    @Override
    public void republishEvents() {

        List<String> aggregateIds = eventStore.getAggregateIds();

        for (String aggregateId : aggregateIds) {
            AccountTransaction accountTransaction = getById(aggregateId);

            if (accountTransaction == null || !accountTransaction.getActive()) {
                continue;
            }

            List<BaseEvent> events = eventStore.getEvents(aggregateId);
            for (BaseEvent event : events) {
                eventProducer.produce(
                        event.getClass().getSimpleName(),
                        event
                );
            }
        }
    }
}
