package com.wallet.app.wallet.adapter.out.persistence.wallet;

import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.UserId;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import org.springframework.stereotype.Component;

@Component
public class WalletPersistenceMapper {

  WalletJpaEntity toEntity(Wallet wallet) {
    return new WalletJpaEntity(
        wallet.id().value(), wallet.userId().value(), wallet.balance().amount(), wallet.version());
  }

  Wallet toDomain(WalletJpaEntity wallet) {
    return Wallet.restore(
        new WalletId(wallet.id()),
        new UserId(wallet.userId()),
        Money.of(wallet.balance()),
        wallet.version());
  }
}
