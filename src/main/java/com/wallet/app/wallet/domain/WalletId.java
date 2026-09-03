package com.wallet.app.wallet.domain;

import java.util.Objects;
import java.util.UUID;

public record WalletId(UUID value) {

  public WalletId {
    Objects.requireNonNull(value, "value must not be null");
  }

  public static WalletId newId() {
    return new WalletId(UUID.randomUUID());
  }
}
