package com.banksolution.cqrs.core.domain;

import com.banksolution.cqrs.core.events.BaseEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public abstract class AggregateRoot {

    protected String id;

    @Setter
    private int version = -1;

    private final List<BaseEvent> changes = new ArrayList<>();

    public List<BaseEvent> getUncommittedChanges() {
        return this.changes;
    }

    public void markChangesAsCommitted() {
        this.changes.clear();
    }

    protected void applyChange(
            BaseEvent event,
            Boolean isNewEvent) {

        try {
            var method = getClass().getDeclaredMethod("apply", event.getClass());
            method.setAccessible(true);
            method.invoke(this, event);
        } catch (NoSuchMethodException e) {
            log.error("The apply method was not found in the aggregate for {}",
                    event.getClass().getName());
        } catch (Exception exception) {
            log.error("Error applying event to aggregate, message: {}, className: {}",
                    exception.getMessage(),
                    event.getClass().getName(),
                    exception);
        } finally {
            if (isNewEvent) {
                changes.add(event);
            }
        }
    }

    protected void raiseEvent(BaseEvent event) {
        applyChange(event, true);
    }

    public void replayEvents(Iterable<BaseEvent> events) {
        events.forEach(event -> applyChange(event, false));
    }
}
