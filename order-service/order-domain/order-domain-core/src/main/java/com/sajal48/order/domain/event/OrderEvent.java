package com.sajal48.order.domain.event;

import com.sajal48.common.domain.event.DomainEvent;
import com.sajal48.order.domain.entity.Order;

import java.time.ZonedDateTime;

public abstract class OrderEvent implements DomainEvent<Order> {
	private Order order;
	private ZonedDateTime createdAt;

	public OrderEvent(Order order, ZonedDateTime createdAt) {
		this.order = order;
		this.createdAt = createdAt;
	}

	public Order getOrder() {
		return order;
	}

	public ZonedDateTime getCreatedAt() {
		return createdAt;
	}
}
