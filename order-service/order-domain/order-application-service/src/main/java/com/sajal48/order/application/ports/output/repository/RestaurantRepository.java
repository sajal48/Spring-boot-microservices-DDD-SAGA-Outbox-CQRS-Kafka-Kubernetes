package com.sajal48.order.application.ports.output.repository;

import com.sajal48.order.domain.entity.Restaurant;

import java.util.Optional;

public interface RestaurantRepository {
	Optional<Restaurant> findRestaurantInformation(Restaurant restaurant);
}
