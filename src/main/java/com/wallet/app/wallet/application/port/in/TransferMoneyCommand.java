package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.WalletId;
import java.util.Objects;

public record TransferMoneyCommand(
    WalletId sourceWalletId, WalletId destinationWalletId, Money amount) {
  public TransferMoneyCommand {
    Objects.requireNonNull(sourceWalletId, "sourceWalletId must not be null");
    Objects.requireNonNull(destinationWalletId, "destinationWalletId must not be null");
    Objects.requireNonNull(amount, "amount must not be null");
  }
}
