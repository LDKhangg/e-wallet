package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.UserId;

import java.util.Objects;

public record CreateWalletCommand(
  UserId userId
) {
  public CreateWalletCommand{
    Objects.requireNonNull(userId,"userId must not be null");
  }
}
