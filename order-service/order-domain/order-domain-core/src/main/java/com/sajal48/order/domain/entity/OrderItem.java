package com.sajal48.order.domain.entity;

import com.sajal48.common.domain.entity.BaseEntity;
import com.sajal48.common.domain.valueobject.Money;
import com.sajal48.common.domain.valueobject.OrderId;
import com.sajal48.order.domain.valueobject.OrderItemId;

public class OrderItem extends BaseEntity<OrderItemId> {
    private OrderId orderId;
    private final Product product;
    private final int quantity;
    private final Money price;
    private final Money subTotal;

     void initializeOrderItem(OrderId orderId , OrderItemId orderItemId) {
        this.orderId = orderId;
        super.setId(orderItemId);
    }
    boolean isPriceValid(){
         return price.isGreaterThanZero() &&
                 price.equals(product.getPrice()) &&
                 price.multiply(quantity).equals(subTotal);
    }

    private OrderItem(Builder builder) {
        super.setId(builder.orderItemId);
        product = builder.product;
        quantity = builder.quantity;
        price = builder.price;
        subTotal = builder.subTotal;
    }


    public OrderId getOrderId() {
        return orderId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Money getPrice() {
        return price;
    }

    public Money getSubTotal() {
        return subTotal;
    }


    public static final class Builder {
        private Product product;
        private int quantity;
        private Money price;
        private Money subTotal;
        private OrderItemId orderItemId;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder product(Product val) {
            product = val;
            return this;
        }

        public Builder quantity(int val) {
            quantity = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder subTotal(Money val) {
            subTotal = val;
            return this;
        }

        public Builder id(OrderItemId val) {
            orderItemId = val;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(this);
        }
    }
}
