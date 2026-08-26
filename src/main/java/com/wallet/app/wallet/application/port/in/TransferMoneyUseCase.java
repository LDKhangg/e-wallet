package com.wallet.app.wallet.application.port.in;

import com.wallet.app.wallet.domain.TransactionId;

public interface TransferMoneyUseCase {
  TransactionId transfer(TransferMoneyCommand command);
}
