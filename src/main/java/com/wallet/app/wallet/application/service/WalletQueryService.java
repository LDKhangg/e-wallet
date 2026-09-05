package com.wallet.app.wallet.application.service;

import com.wallet.app.wallet.application.port.in.WalletNotFoundException;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletQueryService {

  private final WalletRepositoryPort walletRepositoryPort;

  @Cacheable(value = "wallets", key = "#walletId.value()")
  public Wallet getWallet(WalletId walletId) {
    return walletRepositoryPort
        .findById(walletId)
        .orElseThrow(() -> new WalletNotFoundException(walletId));
  }
}
