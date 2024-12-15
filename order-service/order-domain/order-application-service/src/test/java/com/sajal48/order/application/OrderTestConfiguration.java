package com.sajal48.order.application;

import com.sajal48.order.application.mapper.OrderDataMapper;
import com.sajal48.order.application.ports.input.service.OrderApplicationService;
import com.sajal48.order.application.ports.output.publisher.payment.OrderCancelledPaymentRequestMessagePublisher;
import com.sajal48.order.application.ports.output.publisher.payment.OrderCreatedPaymentRequestMessagePublisher;
import com.sajal48.order.application.ports.output.publisher.restaurantapproval.OrderPaidRestaurantRequestMessagePublisher;
import com.sajal48.order.application.ports.output.repository.CustomerRepository;
import com.sajal48.order.application.ports.output.repository.OrderRepository;
import com.sajal48.order.application.ports.output.repository.RestaurantRepository;
import com.sajal48.order.domain.OrderDomainService;
import com.sajal48.order.domain.OrderDomainServiceImpl;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = "com.sajal48.order.application")
public class OrderTestConfiguration {

    @Bean
    public OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCreatedPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderCancelledPaymentRequestMessagePublisher orderCancelledPaymentRequestMessagePublisher() {
        return Mockito.mock(OrderCancelledPaymentRequestMessagePublisher.class);
    }

    @Bean
    public OrderPaidRestaurantRequestMessagePublisher orderPaidRestaurantRequestMessagePublisher() {
        return Mockito.mock(OrderPaidRestaurantRequestMessagePublisher.class);
    }

    @Bean
    public OrderRepository orderRepository() {
        return Mockito.mock(OrderRepository.class);
    }

    @Bean
    public CustomerRepository customerRepository() {
        return Mockito.mock(CustomerRepository.class);
    }

    @Bean
    public RestaurantRepository restaurantRepository() {
        return Mockito.mock(RestaurantRepository.class);
    }

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

    @Bean
    public OrderDataMapper orderDataMapper() {
        return new OrderDataMapper();
    }
    @Bean
    public OrderCreateHelper orderCreateHelper(
            OrderDomainService orderDomainService,
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            RestaurantRepository restaurantRepository,
            OrderDataMapper orderDataMapper) {
        return new OrderCreateHelper(orderDomainService, orderRepository, customerRepository, restaurantRepository, orderDataMapper);
    }

    @Bean
    public OrderCreateCommandHandler orderCreateCommandHandler(
            OrderCreateHelper orderCreateHelper,
            OrderDataMapper orderDataMapper,
            OrderCreatedPaymentRequestMessagePublisher orderCreatedPaymentRequestMessagePublisher) {
        return new OrderCreateCommandHandler(orderCreateHelper, orderDataMapper, orderCreatedPaymentRequestMessagePublisher);
    }

    @Bean
    public OrderTrackCommandHandler orderTrackCommandHandler(OrderRepository orderRepository, OrderDataMapper orderDataMapper) {
        return new OrderTrackCommandHandler(orderRepository, orderDataMapper);
    }

    @Bean
    public OrderApplicationService orderApplicationService(
            OrderCreateCommandHandler orderCreateCommandHandler,
            OrderTrackCommandHandler orderTrackCommandHandler) {
        return new OrderApplicationServiceImpl(orderCreateCommandHandler, orderTrackCommandHandler);
    }
}
