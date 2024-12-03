package com.sajal48.common.domain.event.publisher;

import com.sajal48.common.domain.event.DomainEvent;

public interface DomainEventPublisher<T extends DomainEvent> {
	void publish(T domainEvent);
}
