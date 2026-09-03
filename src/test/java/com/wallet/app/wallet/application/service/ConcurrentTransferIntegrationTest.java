package com.wallet.app.wallet.application.service;

import com.wallet.app.wallet.adapter.out.persistence.wallet.SpringDataWalletRepository;
import com.wallet.app.wallet.adapter.out.persistence.wallet.WalletJpaEntity;
import com.wallet.app.wallet.application.port.in.TransferMoneyCommand;
import com.wallet.app.wallet.application.port.in.TransferMoneyUseCase;
import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.WalletId;
import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public class ConcurrentTransferIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
  }

  @Autowired private TransferMoneyUseCase transferMoneyUseCase;

  @Autowired private SpringDataWalletRepository walletRepository;

  private WalletId sourceWalletId;
  private WalletId dest1Id;
  private WalletId dest2Id;

  @BeforeEach
  void setUp() {
    sourceWalletId = WalletId.newId();
    dest1Id = WalletId.newId();
    dest2Id = WalletId.newId();

    walletRepository.save(
        new WalletJpaEntity(
            sourceWalletId.value(), UUID.randomUUID(), new BigDecimal("10000"), 0L));
    walletRepository.save(
        new WalletJpaEntity(dest1Id.value(), UUID.randomUUID(), new BigDecimal("0"), 0L));
    walletRepository.save(
        new WalletJpaEntity(dest2Id.value(), UUID.randomUUID(), new BigDecimal("0"), 0L));
  }

  @Test
  void shouldHandleConcurrentTransfersWithOptimisticLocking() throws InterruptedException {
    int numberOfThreads = 5;
    ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
    CountDownLatch latch = new CountDownLatch(1);
    AtomicInteger successCount = new AtomicInteger(0);
    AtomicInteger failureCount = new AtomicInteger(0);

    for (int i = 0; i < numberOfThreads; i++) {
      WalletId targetDest = (i % 2 == 0) ? dest1Id : dest2Id;
      executorService.submit(
          () -> {
            try {
              latch.await();
              transferMoneyUseCase.transfer(
                  new TransferMoneyCommand(
                      sourceWalletId, targetDest, Money.of(new BigDecimal("100"))));
              successCount.incrementAndGet();
            } catch (Exception e) {
              if (e.getCause() instanceof ObjectOptimisticLockingFailureException
                  || e instanceof ObjectOptimisticLockingFailureException) {
                failureCount.incrementAndGet();
              }
            }
          });
    }
    latch.countDown();
    executorService.shutdown();
  }
}
