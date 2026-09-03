package com.wallet.app.wallet.domain;

import java.util.Objects;

public final class Wallet {

  private final WalletId id;
  private final UserId userId;
  private final Money balance;
  private final long version;

  private Wallet(WalletId id, UserId userId, Money balance, long version) {
    this.id = Objects.requireNonNull(id, "id must not be null");
    this.userId = Objects.requireNonNull(userId, "userId must not be null");
    this.balance = Objects.requireNonNull(balance, "balance must not be null");
    if (version < 0) {
      throw new IllegalArgumentException("version must not be negative");
    }
    this.version = version;
  }

  public static Wallet restore(WalletId id, UserId userId, Money balance, long version) {
    return new Wallet(id, userId, balance, version);
  }

  public WalletId id() {
    return id;
  }

  public UserId userId() {
    return userId;
  }

  public Money balance() {
    return balance;
  }

  public long version() {
    return version;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Wallet wallet)) {
      return false;
    }
    return version == wallet.version
        && id.equals(wallet.id)
        && userId.equals(wallet.userId)
        && balance.equals(wallet.balance);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, userId, balance, version);
  }

  public Wallet debit(Money amount) {
    Objects.requireNonNull(amount, "amount must not be null");
    if (balance.isLessThan(amount)) {
      throw new InsufficientFundsException(id);
    }

    return new Wallet(id, userId, balance.subtract(amount), version);
  }

  public Wallet credit(Money amount) {
    Objects.requireNonNull(amount, "amount must not be null");
    return new Wallet(id, userId, balance.add(amount), version);
  }

  public static Wallet create(UserId userId) {
    return new Wallet(WalletId.newId(), userId, Money.of(0), 0);
  }
}
