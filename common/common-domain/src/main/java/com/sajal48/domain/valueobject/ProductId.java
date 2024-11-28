package com.sajal48.domain.valueobject;

import java.util.UUID;

public class ProductId extends BaseId<UUID>{
    protected ProductId(UUID value) {
        super(value);
    }
}
