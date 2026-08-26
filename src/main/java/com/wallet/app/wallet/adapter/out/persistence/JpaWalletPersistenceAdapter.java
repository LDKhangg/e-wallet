package com.wallet.app.wallet.adapter.out.persistence;

import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JpaWalletPersistenceAdapter implements WalletRepositoryPort {

    private final SpringDataWalletRepository repository;
    private final WalletPersistenceMapper mapper;

    JpaWalletPersistenceAdapter(SpringDataWalletRepository repository, WalletPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Wallet> findById(WalletId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Wallet save(Wallet wallet) {
        return mapper.toDomain(repository.save(mapper.toEntity(wallet)));
    }
}
