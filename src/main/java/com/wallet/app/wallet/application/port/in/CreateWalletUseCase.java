package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.WalletId;

public interface CreateWalletUseCase {
  WalletId create(CreateWalletCommand command);
}
