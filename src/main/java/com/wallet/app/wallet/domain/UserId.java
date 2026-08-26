package com.wallet.app.wallet.domain;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {

    public UserId {
        Objects.requireNonNull(value, "value must not be null");
    }

    public static UserId newId() {
        return new UserId(UUID.randomUUID());
    }
}
