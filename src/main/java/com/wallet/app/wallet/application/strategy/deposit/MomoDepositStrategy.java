package com.wallet.app.wallet.application.strategy.deposit;

import com.wallet.app.wallet.application.port.in.DepositCommand;
import com.wallet.app.wallet.application.port.in.WalletNotFoundException;
import com.wallet.app.wallet.application.port.out.TransactionRepositoryPort;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Transaction;
import com.wallet.app.wallet.domain.TransactionId;
import com.wallet.app.wallet.domain.Wallet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MomoDepositStrategy implements DepositStrategy {

  private final WalletRepositoryPort walletRepositoryPort;
  private final TransactionRepositoryPort transactionRepositoryPort;

  @Override
  public String getMethodCode() {
    return "MOMO";
  }

  @Override
  public TransactionId deposit(DepositCommand command) {
    Wallet wallet =
        walletRepositoryPort
            .findById(command.walletId())
            .orElseThrow(() -> new WalletNotFoundException(command.walletId()));

    Wallet updatedWallet = wallet.credit(command.amount());
    walletRepositoryPort.save(updatedWallet);

    Transaction tx = Transaction.start(command.walletId(), command.walletId(), command.amount());
    Transaction saved = transactionRepositoryPort.save(tx.complete());
    return saved.id();
  }
}
