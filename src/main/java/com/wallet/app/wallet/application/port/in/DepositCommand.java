package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.WalletId;
import java.util.Objects;

public record DepositCommand(WalletId walletId, Money amount, String methodCode) {
  public DepositCommand {
    Objects.requireNonNull(walletId, "WalletId must not be null");
    Objects.requireNonNull(amount, "amount must not be null");
    Objects.requireNonNull(methodCode, "methodCode must not be null");
    if (methodCode.isBlank()) {
      throw new IllegalArgumentException("methodCode must not be blank");
    }
  }
}
