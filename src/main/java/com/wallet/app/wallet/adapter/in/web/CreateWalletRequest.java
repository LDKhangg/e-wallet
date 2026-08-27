package com.wallet.app.wallet.adapter.in.web;

import java.util.Objects;
import java.util.UUID;

public record CreateWalletRequest(
    UUID userId
) {
    public CreateWalletRequest {
        Objects.requireNonNull(userId, "userId must not be null");
    }
}
