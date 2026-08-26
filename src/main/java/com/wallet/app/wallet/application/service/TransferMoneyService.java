package com.wallet.app.wallet.application.service;

import com.wallet.app.wallet.application.port.in.TransferMoneyCommand;
import com.wallet.app.wallet.application.port.in.TransferMoneyUseCase;
import com.wallet.app.wallet.application.port.in.WalletNotFoundException;
import com.wallet.app.wallet.application.port.out.TransactionRepositoryPort;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Transaction;
import com.wallet.app.wallet.domain.TransactionId;
import com.wallet.app.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TransferMoneyService implements TransferMoneyUseCase {

  private final WalletRepositoryPort walletRepositoryPort;
  private final TransactionRepositoryPort transactionRepositoryPort;

  @Override
  @Transactional
  public TransactionId transfer(TransferMoneyCommand command) {
    Objects.requireNonNull(command,"command must not be null");
    Transaction transaction = Transaction.start(
      command.sourceWalletId(),
      command.destinationWalletId(),
      command.amount());

    Wallet sourceWallet = walletRepositoryPort
      .findById(transaction.sourceWalletId())
      .orElseThrow(()->new WalletNotFoundException(transaction.sourceWalletId()));
    Wallet desitnationWallet = walletRepositoryPort
      .findById(transaction.destinationWalletId())
      .orElseThrow(()-> new WalletNotFoundException(transaction.destinationWalletId()));

    walletRepositoryPort.save(sourceWallet.debit(transaction.amount()));
    walletRepositoryPort.save(desitnationWallet.credit(transaction.amount()));

    Transaction savedTransaction =transactionRepositoryPort.save(transaction.complete());
    return savedTransaction.id();
  }
}
