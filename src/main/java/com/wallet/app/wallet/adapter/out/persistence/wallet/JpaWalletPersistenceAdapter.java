package com.wallet.app.wallet.adapter.out.persistence.wallet;

import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.UserId;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;

@Repository
@RequiredArgsConstructor
class JpaWalletPersistenceAdapter implements WalletRepositoryPort {

    private final SpringDataWalletRepository repository;
    private final WalletPersistenceMapper mapper;

    @Override
    public Optional<Wallet> findById(WalletId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return mapper.toDomain(repository.save(mapper.toEntity(wallet)));
    }

    public Optional<Wallet> findByUserId(UserId userId){
      return repository.findWalletJpaEntitiesByUserId(userId.value()).map(mapper::toDomain);
    }
}
