package com.sajal48.order.application.ports.input.service;

import com.sajal48.order.application.dto.create.CreateOrderCommand;
import com.sajal48.order.application.dto.create.CreateOrderResponse;
import com.sajal48.order.application.dto.track.TrackOrderQuery;
import com.sajal48.order.application.dto.track.TrackOrderResponse;
import jakarta.validation.Valid;

public interface OrderApplicationService {
	CreateOrderResponse createOrder(@Valid CreateOrderCommand createOrderCommand);
	TrackOrderResponse trackOrder(@Valid TrackOrderQuery trackOrderQuery);
}
