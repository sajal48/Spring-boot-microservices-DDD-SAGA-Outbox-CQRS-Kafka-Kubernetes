package com.sajal48.order.application.ports.output.publisher.restaurantapproval;

import com.sajal48.common.domain.event.publisher.DomainEventPublisher;
import com.sajal48.order.domain.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
