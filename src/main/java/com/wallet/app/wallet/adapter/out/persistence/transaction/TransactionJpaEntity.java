package com.wallet.app.wallet.adapter.out.persistence.transaction;

import com.wallet.app.wallet.domain.TransactionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "transactions")
class TransactionJpaEntity {

  @Id private UUID id;

  @Column(name = "from_wallet_id", nullable = false)
  private UUID sourceWalletId;

  @Column(name = "to_wallet_id", nullable = false)
  private UUID destinationWalletId;

  @Column(nullable = false, precision = 19, scale = 0)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 32)
  private TransactionStatus status;

  protected TransactionJpaEntity() {}

  TransactionJpaEntity(
      UUID id,
      UUID sourceWalletId,
      UUID destinationWalletId,
      BigDecimal amount,
      TransactionStatus status) {
    this.id = id;
    this.sourceWalletId = sourceWalletId;
    this.destinationWalletId = destinationWalletId;
    this.amount = amount;
    this.status = status;
  }

  UUID id() {
    return id;
  }

  UUID sourceWalletId() {
    return sourceWalletId;
  }

  UUID destinationWalletId() {
    return destinationWalletId;
  }

  BigDecimal amount() {
    return amount;
  }

  TransactionStatus status() {
    return status;
  }
}
