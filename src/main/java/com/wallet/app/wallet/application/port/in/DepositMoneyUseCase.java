package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.TransactionId;

public interface DepositMoneyUseCase {
  TransactionId deposit(DepositCommand command);
}
