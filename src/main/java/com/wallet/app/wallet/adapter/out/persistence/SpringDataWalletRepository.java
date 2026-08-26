package com.wallet.app.wallet.adapter.out.persistence;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataWalletRepository extends JpaRepository<WalletJpaEntity, UUID> {
}
