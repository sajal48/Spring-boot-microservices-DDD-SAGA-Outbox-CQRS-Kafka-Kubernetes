package com.sajal48.order.domain.entity;

import com.sajal48.common.domain.entity.BaseEntity;
import com.sajal48.common.domain.valueobject.Money;
import com.sajal48.common.domain.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public Product(ProductId productId ,String name, Money price) {
        super.setId(productId);
        this.name = name;
        this.price = price;
    }

    public Product(ProductId productId) {
        super.setId(productId);
    }

    public void updateWithConfirmNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }
}
