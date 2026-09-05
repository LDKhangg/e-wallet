package com.wallet.app.wallet.domain.event;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

public record MoneyTransferredEvent(
    String transactionId,
    String sourceWalletId,
    String destinationWalletId,
    BigDecimal amount,
    Instant timestamp)
    implements Serializable {}
