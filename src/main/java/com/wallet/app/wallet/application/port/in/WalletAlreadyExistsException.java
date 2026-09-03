package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.UserId;

public class WalletAlreadyExistsException extends RuntimeException {
  private final UserId userId;

  public WalletAlreadyExistsException(UserId userId) {
    super("wallet already exists for user " + userId.value());
    this.userId = userId;
  }

  public UserId userId() {
    return userId;
  }
}
