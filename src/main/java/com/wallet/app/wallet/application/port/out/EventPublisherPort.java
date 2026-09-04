package com.wallet.app.wallet.application.port.out;

import com.wallet.app.wallet.domain.event.MoneyTransferredEvent;

public interface EventPublisherPort {
  void publishMoneyTransferred(MoneyTransferredEvent event);
}
