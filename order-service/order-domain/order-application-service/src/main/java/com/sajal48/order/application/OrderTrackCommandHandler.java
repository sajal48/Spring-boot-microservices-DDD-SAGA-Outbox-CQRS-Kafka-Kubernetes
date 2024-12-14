package com.sajal48.order.application;

import com.sajal48.order.application.dto.track.TrackOrderQuery;
import com.sajal48.order.application.dto.track.TrackOrderResponse;
import com.sajal48.order.application.mapper.OrderDataMapper;
import com.sajal48.order.application.ports.output.repository.OrderRepository;
import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.exception.OrderNotFoundException;
import com.sajal48.order.domain.valueobject.TrackingId;
import lombok.extern.apachecommons.CommonsLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@CommonsLog
public class OrderTrackCommandHandler {
    private final OrderRepository orderRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderTrackCommandHandler(
            OrderRepository orderRepository,
            OrderDataMapper orderDataMapper) {
        this.orderRepository = orderRepository;
        this.orderDataMapper = orderDataMapper;
    }
    @Transactional(readOnly = true)
    public TrackOrderResponse trackOrder(TrackOrderQuery trackOrderQuery) {
        Optional<Order> orderResult = orderRepository.findByTrackingId(
                new TrackingId(trackOrderQuery.getOrderTrackingId()));
        if(orderResult.isEmpty()){
            log.warn("Could not find order with tracking id :{}", trackOrderQuery.getOrderTrackingId());
            throw new OrderNotFoundException("Could not find order with tracking id: "+ trackOrderQuery.getOrderTrackingId());
        }
        return orderDataMapper.orderToTrackOrderResponse(orderResult.get());
    }
}
