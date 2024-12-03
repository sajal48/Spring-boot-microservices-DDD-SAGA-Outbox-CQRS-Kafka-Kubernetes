package com.sajal48.order.application.ports.output.publisher.payment;

import com.sajal48.common.domain.event.publisher.DomainEventPublisher;
import com.sajal48.order.domain.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}