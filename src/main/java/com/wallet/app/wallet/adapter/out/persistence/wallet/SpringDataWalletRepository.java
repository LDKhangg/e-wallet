package com.wallet.app.wallet.adapter.out.persistence.wallet;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface SpringDataWalletRepository extends JpaRepository<WalletJpaEntity, UUID> {
  Optional<WalletJpaEntity> findWalletJpaEntitiesByUserId(UUID userId);
}
