package com.wallet.app.wallet.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public record Money(BigDecimal amount) {

    private static final BigDecimal MAX_AMOUNT = new BigDecimal("9999999999999999999");

    public Money {
        Objects.requireNonNull(amount, "amount must not be null");
        try {
            amount = amount.setScale(0, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException exception) {
            throw new IllegalArgumentException("amount must not have fractional VND", exception);
        }
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("amount must not be negative");
        }
        if (amount.compareTo(MAX_AMOUNT) > 0) {
            throw new IllegalArgumentException("amount exceeds maximum persistable VND");
        }
    }

    public static Money of(BigDecimal amount) {
        return new Money(amount);
    }

    public static Money of(long amount) {
        return of(BigDecimal.valueOf(amount));
    }
}
