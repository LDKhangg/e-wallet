package com.wallet.app.wallet.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.UserId;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class JpaWalletPersistenceAdapterTest {

  @Container @ServiceConnection
  static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Autowired WalletRepositoryPort wallets;

  @Test
  void saves_and_restores_wallet() {
    Wallet wallet = Wallet.restore(WalletId.newId(), UserId.newId(), Money.of(100_000), 0);

    wallets.save(wallet);

    assertThat(wallets.findById(wallet.id())).contains(wallet);
  }
}
