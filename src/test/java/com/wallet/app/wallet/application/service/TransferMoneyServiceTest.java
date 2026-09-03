package com.wallet.app.wallet.application.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wallet.app.wallet.application.port.in.TransferMoneyCommand;
import com.wallet.app.wallet.application.port.in.WalletNotFoundException;
import com.wallet.app.wallet.application.port.out.TransactionRepositoryPort;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.*;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TransferMoneyServiceTest {
  @Mock private WalletRepositoryPort walletRepositoryPort;

  @Mock private TransactionRepositoryPort transactionRepositoryPort;

  @InjectMocks private TransferMoneyService transferMoneyService;

  private Wallet sourceWallet;
  private Wallet destinationWallet;
  private WalletId sourceId;
  private WalletId destinationId;

  @BeforeEach
  void setUp() {
    sourceId = new WalletId(UUID.randomUUID());
    destinationId = new WalletId(UUID.randomUUID());

    sourceWallet =
        Wallet.restore(
            sourceId, new UserId(UUID.randomUUID()), Money.of(new BigDecimal("1000")), 1L);
    destinationWallet =
        Wallet.restore(
            destinationId, new UserId(UUID.randomUUID()), Money.of(new BigDecimal("500")), 1L);
  }

  @Test
  @DisplayName("Should transfer money succecssfully when wallets exist and have sufficient funds")
  void shouldTransferMoneySuccessfully() {
    TransferMoneyCommand command =
        new TransferMoneyCommand(sourceId, destinationId, Money.of(new BigDecimal("200")));
    given(walletRepositoryPort.findById(sourceId)).willReturn(Optional.of(sourceWallet));
    given(walletRepositoryPort.findById(destinationId)).willReturn(Optional.of(destinationWallet));
    given(walletRepositoryPort.save(any(Wallet.class)))
        .willAnswer(invocation -> invocation.getArgument(0));
    given(transactionRepositoryPort.save(any(Transaction.class)))
        .willAnswer(
            invocation -> {
              Transaction tx = invocation.getArgument(0);
              return Transaction.restore(
                  new TransactionId(UUID.randomUUID()),
                  tx.sourceWalletId(),
                  tx.destinationWalletId(),
                  tx.amount(),
                  tx.status());
            });

    TransactionId transactionId = transferMoneyService.transfer(command);

    assertThat(transactionId).isNotNull();
    verify(walletRepositoryPort, times(2)).save(any(Wallet.class));
    verify(transactionRepositoryPort).save(any(Transaction.class));
  }

  @Test
  @DisplayName("Should throw WalletNotFoundException when source wallet does not exist")
  void shouldThrowExceptionWhenSourceWalletNotFound() {
    TransferMoneyCommand command =
        new TransferMoneyCommand(sourceId, destinationId, Money.of(new BigDecimal("200")));

    given(walletRepositoryPort.findById(sourceId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> transferMoneyService.transfer(command))
        .isInstanceOf(WalletNotFoundException.class);
  }
}
