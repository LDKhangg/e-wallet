package com.wallet.app.wallet.application.port.out;

import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import java.util.Optional;

public interface WalletRepositoryPort {

    Optional<Wallet> findById(WalletId id);

    Wallet save(Wallet wallet);
}
