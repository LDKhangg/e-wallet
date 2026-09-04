package com.wallet.app.wallet.adapter.out.bank;

import com.wallet.app.wallet.application.port.out.BankIntegrationPort;
import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.UserId;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ExternalBankAdapter implements BankIntegrationPort {
  @Override
  @CircuitBreaker(name = "bankService", fallbackMethod = "depositFallback")
  @Retry(name = "bankService")
  public boolean depositToBank(UserId userId, Money amount) {
    log.info("Calling external bank API for user {} with amount {}", userId.value(), amount);
    return true;
  }

  private boolean depositFallback(UserId userId, Throwable t) {
    log.error("Fallback triggered for user{} due to: {}", userId.value(), t.getMessage());
    return false;
  }
}
