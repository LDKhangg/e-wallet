package com.wallet.app.wallet.application.port.out;

import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.UserId;

public interface BankIntegrationPort {
  boolean depositToBank(UserId userId, Money amount);
}
