package com.wallet.app.wallet.application.strategy.deposit;

import com.wallet.app.wallet.application.port.in.DepositCommand;
import com.wallet.app.wallet.domain.TransactionId;

public interface DepositStrategy {
  String getMethodCode();

  TransactionId deposit(DepositCommand command);
}
