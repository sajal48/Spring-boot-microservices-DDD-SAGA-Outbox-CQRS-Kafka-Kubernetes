package com.sajal48.order.domain;

import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.entity.Product;
import com.sajal48.order.domain.entity.Restaurant;
import com.sajal48.order.domain.event.OrderCancelledEvent;
import com.sajal48.order.domain.event.OrderCreatedEvent;
import com.sajal48.order.domain.event.OrderPaidEvent;
import com.sajal48.order.domain.exception.OrderDomainException;
import lombok.extern.slf4j.Slf4j;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
public class OrderDomainServiceImpl implements OrderDomainService {
	private static final String UTC = "UTC";
	@Override
	public OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant) {
		validateRestaurant(restaurant);
		setOrderProductInformation(order, restaurant);
		order.validateOrder();
		order.initializeOrder();
		log.info("Order with restaurantId: {} initiated", order.getId().getValue());
		return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
	}

	@Override
	public OrderPaidEvent payOrder(Order order) {
		order.pay();
		log.info("Order with restaurantId: {} paid successfully", order.getId().getValue());
		return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
	}

	@Override
	public void approveOrder(Order order) {
		order.approve();
		log.info("Order with restaurantId: {} is approved", order.getId().getValue());
	}

	@Override
	public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
		order.initCancel(failureMessages);
		log.info("Order payment is cancelling for order with restaurantId: {}", order.getId().getValue());
		return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of(UTC)));
	}

	@Override
	public void cancelOrder(Order order, List<String> failureMessages) {
		order.cancel(failureMessages);
		log.info("Order with restaurantId :{} is cancelled", order.getId().getValue());
	}

	private void validateRestaurant(Restaurant restaurant) {
		if(!restaurant.isActive()){
			throw new OrderDomainException("Restaurant with restaurantId: " + restaurant.getId().getValue() + " is currently not active");
		}
	}

	private void setOrderProductInformation(Order order, Restaurant restaurant) {
		order.getItems().forEach(orderItem -> restaurant.getProducts().forEach(restaurantProduct -> {
			Product currentProduct = orderItem.getProduct();
			if(currentProduct.equals(restaurantProduct)){
				currentProduct.updateWithConfirmNameAndPrice(restaurantProduct.getName(),restaurantProduct.getPrice());
			}
		}));
	}
}
