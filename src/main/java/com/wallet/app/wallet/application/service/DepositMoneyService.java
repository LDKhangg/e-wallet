package com.wallet.app.wallet.application.service;

import com.wallet.app.wallet.application.port.in.DepositCommand;
import com.wallet.app.wallet.application.port.in.DepositMoneyUseCase;
import com.wallet.app.wallet.application.port.out.TransactionRepositoryPort;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.application.strategy.deposit.DepositStrategy;
import com.wallet.app.wallet.domain.TransactionId;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositMoneyService implements DepositMoneyUseCase {

  private final Map<String, DepositStrategy> strategyMap;
  private final WalletRepositoryPort walletRepositoryPort;
  private final TransactionRepositoryPort transactionRepositoryPort;

  public DepositMoneyService(
      List<DepositStrategy> strategies,
      WalletRepositoryPort walletRepositoryPort,
      TransactionRepositoryPort transactionRepositoryPort) {
    this.strategyMap =
        strategies.stream()
            .collect(Collectors.toMap(DepositStrategy::getMethodCode, Function.identity()));
    this.walletRepositoryPort = walletRepositoryPort;
    this.transactionRepositoryPort = transactionRepositoryPort;
  }

  @Override
  @Transactional
  public TransactionId deposit(DepositCommand command) {
    Objects.requireNonNull(command, "command must not be null");
    DepositStrategy strategy = strategyMap.get(command.methodCode());
    if (strategy == null) {
      throw new IllegalArgumentException(
          "Unsupported deposit method code: " + command.methodCode());
    }

    return strategy.deposit(command);
  }
}
