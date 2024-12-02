package com.sajal48.order.domain.event;

import com.sajal48.order.domain.entity.Order;

import java.time.ZonedDateTime;

public class OrderCancelledEvent extends  OrderEvent{
	public OrderCancelledEvent(Order order, ZonedDateTime createdAt) {
		super(order, createdAt);
	}
}