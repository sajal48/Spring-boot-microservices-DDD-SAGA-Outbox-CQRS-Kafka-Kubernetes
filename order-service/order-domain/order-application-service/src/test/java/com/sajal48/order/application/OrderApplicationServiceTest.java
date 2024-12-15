package com.sajal48.order.application;

import com.sajal48.common.domain.valueobject.CustomerId;
import com.sajal48.common.domain.valueobject.Money;
import com.sajal48.common.domain.valueobject.OrderId;
import com.sajal48.common.domain.valueobject.OrderStatus;
import com.sajal48.common.domain.valueobject.ProductId;
import com.sajal48.common.domain.valueobject.RestaurantId;
import com.sajal48.order.application.dto.create.CreateOrderCommand;
import com.sajal48.order.application.dto.create.CreateOrderResponse;
import com.sajal48.order.application.dto.create.OrderAddress;
import com.sajal48.order.application.dto.create.OrderItem;
import com.sajal48.order.application.mapper.OrderDataMapper;
import com.sajal48.order.application.ports.input.service.OrderApplicationService;
import com.sajal48.order.application.ports.output.repository.CustomerRepository;
import com.sajal48.order.application.ports.output.repository.OrderRepository;
import com.sajal48.order.application.ports.output.repository.RestaurantRepository;
import com.sajal48.order.domain.entity.Customer;
import com.sajal48.order.domain.entity.Order;
import com.sajal48.order.domain.entity.Product;
import com.sajal48.order.domain.entity.Restaurant;
import com.sajal48.order.domain.exception.OrderDomainException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(classes = {OrderTestConfiguration.class})
public class OrderApplicationServiceTest {
    @Autowired
    private OrderApplicationService orderApplicationService;
    @Autowired
    private OrderDataMapper orderDataMapper;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;

    private CreateOrderCommand createOrderCommand;
    private CreateOrderCommand createOrderCommandWrongPrice;
    private CreateOrderCommand createOrderCommandWrongProductPrice;
    private final UUID CUSTOMER_ID = UUID.fromString("bbc5f1c0-09ae-41be-9299-38ba0d261905");
    private final UUID RESTAURANT_ID = UUID.fromString("bbc5f1c0-09ae-41be-9299-38ba0d261905");
    private final UUID PRODUCT_ID = UUID.fromString("bbc5f1c0-09ae-41be-9299-38ba0d261905");
    private final UUID ORDER_ID = UUID.fromString("bbc5f1c0-09ae-41be-9299-38ba0d261905");
    private final BigDecimal PRICE = new BigDecimal("200.00");

    @BeforeAll
    public void init() {
        createOrderCommand = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .city("city_1")
                        .postalCode("postal_code_1")
                        .build())
                .price(PRICE)
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();

        createOrderCommandWrongPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .city("city_1")
                        .postalCode("postal_code_1")
                        .build())
                .price(new BigDecimal("250.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("50.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        createOrderCommandWrongProductPrice = CreateOrderCommand.builder()
                .customerId(CUSTOMER_ID)
                .restaurantId(RESTAURANT_ID)
                .address(OrderAddress.builder()
                        .street("street_1")
                        .city("city_1")
                        .postalCode("postal_code_1")
                        .build())
                .price(new BigDecimal("210.00"))
                .items(List.of(OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(1)
                                .price(new BigDecimal("60.00"))
                                .subTotal(new BigDecimal("60.00"))
                                .build(),
                        OrderItem.builder()
                                .productId(PRODUCT_ID)
                                .quantity(3)
                                .price(new BigDecimal("50.00"))
                                .subTotal(new BigDecimal("150.00"))
                                .build()))
                .build();
        Customer customer = new Customer();
        customer.setId(new CustomerId(CUSTOMER_ID));
        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(true)
                .build();
        Order order = orderDataMapper.createOrderCommandToOrder(createOrderCommand);
        order.setId(new OrderId(ORDER_ID));
        when(customerRepository.findCustomer(CUSTOMER_ID)).thenReturn(Optional.of(customer));
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand)))
                .thenReturn(Optional.of(restaurantResponse));
        when(orderRepository.save(any(Order.class))).thenReturn(order);
    }

    @Test
    void testCreateOrder() {
        CreateOrderResponse createOrderResponse = orderApplicationService.createOrder(createOrderCommand);
        assertEquals(OrderStatus.PENDING, createOrderResponse.getOrderStatus());
        assertEquals("Order created successfully", createOrderResponse.getMessage());
        assertNotNull(createOrderResponse.getOrderTrackingId());
    }

    @Test
    void testCreateOrderWrongTotalPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () ->
                orderApplicationService.createOrder(createOrderCommandWrongPrice));
        assertEquals("Total price 250.00 does not match price 200.00!", orderDomainException.getMessage());
    }

    @Test
    void testCreateOrderWrongProductPrice() {
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () ->
                orderApplicationService.createOrder(createOrderCommandWrongProductPrice));
        assertEquals("OrderItem price 60.00 is not valid for product " + PRODUCT_ID + "!", orderDomainException.getMessage());
    }

    @Test
    void testCreateOrderWithPassiveRestaurant() {
        Restaurant restaurantResponse = Restaurant.builder()
                .restaurantId(new RestaurantId(RESTAURANT_ID))
                .products(List.of(
                        new Product(new ProductId(PRODUCT_ID), "product-1", new Money(new BigDecimal("50.00"))),
                        new Product(new ProductId(PRODUCT_ID), "product-2", new Money(new BigDecimal("50.00")))))
                .active(false)
                .build();
        when(restaurantRepository.findRestaurantInformation(orderDataMapper.createOrderCommandToRestaurant(createOrderCommand))).thenReturn(Optional.of(restaurantResponse));
        OrderDomainException orderDomainException = assertThrows(OrderDomainException.class, () ->
                orderApplicationService.createOrder(createOrderCommand));
        assertEquals("Restaurant with restaurantId: " + RESTAURANT_ID + " is currently not active", orderDomainException.getMessage());
    }

}
