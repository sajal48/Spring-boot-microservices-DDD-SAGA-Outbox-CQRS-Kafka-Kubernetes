package com.sajal48.order.application.ports;

import com.sajal48.order.application.dto.create.CreateOrderCommand;
import com.sajal48.order.application.dto.create.CreateOrderResponse;
import com.sajal48.order.application.dto.track.TrackOrderQuery;
import com.sajal48.order.application.dto.track.TrackOrderResponse;
import com.sajal48.order.application.ports.input.service.OrderApplicationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Slf4j
@Validated
@Service
class OrderApplicationServiceImpl implements OrderApplicationService {
    private final OrderCreateCommandHandler orderCreateCommandHandler;
    private final OrderTrackCommandHandler orderTrackCommandHandler;

    OrderApplicationServiceImpl(
            OrderCreateCommandHandler orderCreateCommandHandler,
            OrderTrackCommandHandler orderTrackCommandHandler
    ) {
        this.orderCreateCommandHandler = orderCreateCommandHandler;
        this.orderTrackCommandHandler = orderTrackCommandHandler;
    }

    @Override
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        return orderCreateCommandHandler.createOrder(createOrderCommand);
    }

    @Override
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        return orderTrackCommandHandler.trackOrder(trackOrderQuery);
    }
}
