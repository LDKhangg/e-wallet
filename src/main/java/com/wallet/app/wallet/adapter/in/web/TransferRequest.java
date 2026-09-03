package com.wallet.app.wallet.adapter.in.web;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public record TransferRequest(UUID sourceWalletId, UUID destinationWalletId, BigDecimal amount) {
  public TransferRequest {
    Objects.requireNonNull(sourceWalletId, "sourceWalletId must not be null");
    Objects.requireNonNull(destinationWalletId, "destinationWalletId must not be null");
    Objects.requireNonNull(amount, "amount must not be null");
  }
}
