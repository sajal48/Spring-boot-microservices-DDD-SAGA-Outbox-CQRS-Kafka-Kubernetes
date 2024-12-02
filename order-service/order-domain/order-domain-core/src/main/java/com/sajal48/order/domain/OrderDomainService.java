package com.sajal48.order.domain;

import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.entity.Restaurant;
import com.sajal48.order.domain.event.OrderCancelledEvent;
import com.sajal48.order.domain.event.OrderCreatedEvent;
import com.sajal48.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

	OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
	OrderPaidEvent payOrder(Order order);
	void approveOrder(Order order);
	OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);
	void cancelOrder(Order order, List<String> failureMessages);
}
