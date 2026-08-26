package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.WalletId;

public class WalletNotFoundException extends RuntimeException {
  private final WalletId walletId;
  public WalletNotFoundException(WalletId walletId) {
    super("wallet "+walletId.value() +" not found!!");
    this.walletId=walletId;
  }

  public WalletId walletId(){
    return walletId;
  }
}
