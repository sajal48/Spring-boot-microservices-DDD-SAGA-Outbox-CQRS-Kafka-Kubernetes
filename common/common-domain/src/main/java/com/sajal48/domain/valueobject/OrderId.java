package com.sajal48.domain.valueobject;

import java.util.UUID;

public abstract class OrderId extends BaseId<UUID>{
    protected OrderId(UUID value) {
        super(value);
    }
}
