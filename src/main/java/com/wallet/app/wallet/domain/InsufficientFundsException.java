package com.wallet.app.wallet.domain;

public final class InsufficientFundsException extends RuntimeException {
  public InsufficientFundsException(WalletId walletId) {
    super("wallet " + walletId.value() + " has insufficient funds");
  }
}
