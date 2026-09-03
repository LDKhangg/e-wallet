package com.wallet.app.wallet.domain;

import java.util.Objects;
import java.util.UUID;

public record TransactionId(UUID value) {

  public TransactionId {
    Objects.requireNonNull(value, "value must not be null");
  }

  public static TransactionId newId() {
    return new TransactionId(UUID.randomUUID());
  }
}
