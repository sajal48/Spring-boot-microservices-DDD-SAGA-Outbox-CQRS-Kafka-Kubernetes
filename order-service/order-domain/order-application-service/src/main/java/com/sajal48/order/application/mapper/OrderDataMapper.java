package com.sajal48.order.application.mapper;

import com.sajal48.common.domain.valueobject.CustomerId;
import com.sajal48.common.domain.valueobject.Money;
import com.sajal48.common.domain.valueobject.ProductId;
import com.sajal48.common.domain.valueobject.RestaurantId;
import com.sajal48.order.application.dto.create.CreateOrderCommand;
import com.sajal48.order.application.dto.create.CreateOrderResponse;
import com.sajal48.order.application.dto.create.OrderAddress;
import com.sajal48.order.application.dto.track.TrackOrderResponse;
import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.entity.OrderItem;
import com.sajal48.order.domain.entity.Product;
import com.sajal48.order.domain.entity.Restaurant;
import com.sajal48.order.domain.valueobject.StreetAddress;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderDataMapper {
    public Restaurant createOrderCommandToRestaurant(CreateOrderCommand createOrderCommand) {
        return Restaurant.builder()
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .products(createOrderCommand.getItems().stream().map(orderItem ->
                        new Product(new ProductId(orderItem.getProductId())))
                        .collect(Collectors.toList()))
                .build();
    }
    public Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand) {
        return Order.builder()
                .customerId(new CustomerId(createOrderCommand.getCustomerId()))
                .restaurantId(new RestaurantId(createOrderCommand.getRestaurantId()))
                .deliveryAddress(orderAddressToStreetAddress(createOrderCommand.getAddress()))
                .price(new Money(createOrderCommand.getPrice()))
                .items(orderItemsToOrderItemEntities(createOrderCommand.getItems()))
                .build();
    }
    public TrackOrderResponse orderToTrackOrderResponse(Order order) {
        return TrackOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .failureMessages(order.getFailureMessages())
                .build();
    }

    public CreateOrderResponse orderToCreateOrderResponse(Order order,String message) {
        return CreateOrderResponse.builder()
                .orderTrackingId(order.getTrackingId().getValue())
                .orderStatus(order.getOrderStatus())
                .message(message)
                .build();
    }

    private List<OrderItem> orderItemsToOrderItemEntities(
            List<com.sajal48.order.application.dto.create.OrderItem> orderItems) {
            return orderItems.stream().map(orderItem ->
                    OrderItem.builder()
                            .product(new Product(new ProductId(orderItem.getProductId())))
                            .quantity(orderItem.getQuantity())
                            .price(new Money(orderItem.getPrice()))
                            .subTotal(new Money(orderItem.getSubTotal()))
                            .build()).collect(Collectors.toList());
    }

    private StreetAddress orderAddressToStreetAddress(OrderAddress orderAddress) {
        return new StreetAddress(
                UUID.randomUUID(),
                orderAddress.getStreet(),
                orderAddress.getPostalCode(),
                orderAddress.getCity()
                );
    }
}
