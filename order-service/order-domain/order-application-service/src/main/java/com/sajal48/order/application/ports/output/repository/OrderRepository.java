package com.sajal48.order.application.ports.output.repository;

import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {
	Order save(Order order);
	Optional<Order> findByTrackingId(TrackingId trackingId);
}
