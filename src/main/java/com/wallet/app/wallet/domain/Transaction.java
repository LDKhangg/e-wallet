package com.wallet.app.wallet.domain;

import java.util.Objects;

public final class Transaction {

    private final TransactionId id;
    private final WalletId sourceWalletId;
    private final WalletId destinationWalletId;
    private final Money amount;
    private final TransactionStatus status;

    private Transaction(
            TransactionId id,
            WalletId sourceWalletId,
            WalletId destinationWalletId,
            Money amount,
            TransactionStatus status) {
        this.id = Objects.requireNonNull(id, "id must not be null");
        this.sourceWalletId = Objects.requireNonNull(sourceWalletId, "sourceWalletId must not be null");
        this.destinationWalletId = Objects.requireNonNull(destinationWalletId, "destinationWalletId must not be null");
        if (this.sourceWalletId.equals(this.destinationWalletId)){
          throw new IllegalArgumentException(
            "source and destination wallets must be different"
          );
        }
        this.amount = Objects.requireNonNull(amount, "amount must not be null");
        if (amount.amount().signum() == 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        this.status = Objects.requireNonNull(status, "status must not be null");
    }

    public static Transaction restore(
            TransactionId id,
            WalletId sourceWalletId,
            WalletId destinationWalletId,
            Money amount,
            TransactionStatus status) {
        return new Transaction(id, sourceWalletId, destinationWalletId, amount, status);
    }

    public TransactionId id() {
        return id;
    }

    public WalletId sourceWalletId() {
        return sourceWalletId;
    }

    public WalletId destinationWalletId() {
        return destinationWalletId;
    }

    public Money amount() {
        return amount;
    }

    public TransactionStatus status() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Transaction transaction)) {
            return false;
        }
        return id.equals(transaction.id)
                && sourceWalletId.equals(transaction.sourceWalletId)
                && destinationWalletId.equals(transaction.destinationWalletId)
                && amount.equals(transaction.amount)
                && status == transaction.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sourceWalletId, destinationWalletId, amount, status);
    }

    public static Transaction start(WalletId sourceWalletId,
                                  WalletId destinationWalletId,
                                  Money amount){
      return new Transaction(
        TransactionId.newId(),
        sourceWalletId,
        destinationWalletId,
        amount,
        TransactionStatus.PENDING);
    }
    public Transaction complete(){
      if(status!=TransactionStatus.PENDING){
        throw new IllegalStateException(
          "only pending transactions can be completed"
        );
      }

      return new Transaction(
        id,
        sourceWalletId,
        destinationWalletId,
        amount,
        TransactionStatus.COMPLETED
      );
    }

}
