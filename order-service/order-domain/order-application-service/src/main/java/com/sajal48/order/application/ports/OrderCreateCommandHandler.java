package com.sajal48.order.application.ports;

import com.sajal48.order.application.dto.create.CreateOrderCommand;
import com.sajal48.order.application.dto.create.CreateOrderResponse;
import com.sajal48.order.application.mapper.OrderDataMapper;
import com.sajal48.order.application.ports.output.repository.CustomerRepository;
import com.sajal48.order.application.ports.output.repository.OrderRepository;
import com.sajal48.order.application.ports.output.repository.RestaurantRepository;
import com.sajal48.order.domain.OrderDomainService;
import com.sajal48.order.domain.entity.Customer;
import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.entity.Restaurant;
import com.sajal48.order.domain.event.OrderCreatedEvent;
import com.sajal48.order.domain.exception.OrderDomainException;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Component
public class OrderCreateCommandHandler {
    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final RestaurantRepository restaurantRepository;
    private final OrderDataMapper orderDataMapper;

    public OrderCreateCommandHandler(
            OrderDomainService orderDomainService,
            OrderRepository orderRepository,
            CustomerRepository customerRepository,
            RestaurantRepository restaurantRepository,
            OrderDataMapper orderDataMapper
    ) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.restaurantRepository = restaurantRepository;
        this.orderDataMapper = orderDataMapper;
    }

    @Transactional
    public CreateOrderResponse createOrder(CreateOrderCommand createOrderCommand) {
        checkCustomer(createOrderCommand.getCustomerId());
        Restaurant restaurant = checkRestaurant(createOrderCommand);
        restaurantRepository.findRestaurantInformation(restaurant);
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, restaurant);
        Order savedOrder = orderRepository.save(order);
        log.info("Order is created with id: {}", savedOrder.getId().getValue());
        return orderDataMapper.orderToCreateOrderResponse(savedOrder);
    }



    private Restaurant checkRestaurant(CreateOrderCommand createOrderCommand) {
        Restaurant restaurant = orderDataMapper.createOrderCommandToRestaurant(createOrderCommand);
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findRestaurantInformation(restaurant);
        if (optionalRestaurant.isEmpty()) {
            log.warn("Could not find Restaurant with restaurantId: {}", createOrderCommand.getRestaurantId());
            throw new OrderDomainException("Could not find Restaurant with restaurantId: " +
                    createOrderCommand.getRestaurantId());
        }
        return optionalRestaurant.get();
    }

    private void checkCustomer(UUID customerId) {
        Optional<Customer> customer = customerRepository.findCustomer(customerId);
        if (customer.isEmpty()) {
            log.warn("Could not find Customer with restaurantId: {}", customerId);
            throw new OrderDomainException("Could not find Customer with restaurantId: " + customerId);
        }
    }

    private Order saveOrder(Order order) {
       Order orderResult = orderRepository.save(order);
       if(orderResult == null) {
           log.warn("Could not save order!");
           throw new OrderDomainException("Could not save order!");
       }
       log.info("Saved order: {}", orderResult.getId().getValue());
       return orderResult;
    }
}
