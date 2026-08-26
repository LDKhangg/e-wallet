package com.wallet.app.wallet.adapter.out.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "wallets")
class WalletJpaEntity {

    @Id
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(nullable = false, precision = 19, scale = 0)
    private BigDecimal balance;

    @Version
    @Column(nullable = false)
    private long version;

    protected WalletJpaEntity() {
    }

    WalletJpaEntity(UUID id, UUID userId, BigDecimal balance, long version) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.version = version;
    }

    UUID id() {
        return id;
    }

    UUID userId() {
        return userId;
    }

    BigDecimal balance() {
        return balance;
    }

    long version() {
        return version;
    }
}
