package com.wallet.app.wallet.e2e;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.app.wallet.adapter.in.web.DepositRequest;
import com.wallet.app.wallet.application.port.in.TransferMoneyCommand;
import com.wallet.app.wallet.application.port.in.TransferMoneyUseCase;
import com.wallet.app.wallet.application.port.out.EventPublisherPort;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.UserId;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class WalletE2EIntegrationTest {

  @Container
  static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine");

  @Container
  static GenericContainer<?> redis =
      new GenericContainer<>("redis:7-alpine").withExposedPorts(6379);

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.data.redis.host", redis::getHost);
    registry.add("spring.data.redis.port", redis::getFirstMappedPort);
  }

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @Autowired private WalletRepositoryPort walletRepositoryPort;
  @Autowired private TransferMoneyUseCase transferMoneyUseCase;

  @MockitoBean private EventPublisherPort eventPublisherPort;

  @Test
  void shouldPerformDepositAndTransferWorkflowEndToEnd() throws Exception {
    UserId userId = new UserId(UUID.randomUUID());
    WalletId wallet1Id = WalletId.newId();
    WalletId wallet2Id = WalletId.newId();

    walletRepositoryPort.save(
        Wallet.restore(wallet1Id, userId, Money.of(BigDecimal.valueOf(1000)), 0L));
    walletRepositoryPort.save(
        Wallet.restore(
            wallet2Id, new UserId(UUID.randomUUID()), Money.of(BigDecimal.valueOf(100)), 0L));

    // 1. Test Deposit via dynamic strategy (MOMO)
    DepositRequest depositRequest =
        new DepositRequest(wallet1Id.value(), BigDecimal.valueOf(500), "MOMO");

    mockMvc
        .perform(
            post("/api/wallets/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(depositRequest)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.transactionId").exists())
        .andExpect(jsonPath("$.message").value("Deposit successful"));

    // Verify wallet 1 balance updated to 1500
    Wallet updatedWallet1 = walletRepositoryPort.findById(wallet1Id).orElseThrow();
    assertThat(updatedWallet1.balance().amount()).isEqualByComparingTo(BigDecimal.valueOf(1500));

    // 2. Test Transfer workflow
    transferMoneyUseCase.transfer(
        new TransferMoneyCommand(wallet1Id, wallet2Id, Money.of(BigDecimal.valueOf(300))));

    Wallet finalWallet1 = walletRepositoryPort.findById(wallet1Id).orElseThrow();
    Wallet finalWallet2 = walletRepositoryPort.findById(wallet2Id).orElseThrow();

    assertThat(finalWallet1.balance().amount()).isEqualByComparingTo(BigDecimal.valueOf(1200));
    assertThat(finalWallet2.balance().amount()).isEqualByComparingTo(BigDecimal.valueOf(400));
  }
}
