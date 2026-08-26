package com.wallet.app.wallet.adapter.out.persistence;

import com.wallet.app.wallet.application.port.out.TransactionRepositoryPort;
import com.wallet.app.wallet.domain.Transaction;
import com.wallet.app.wallet.domain.TransactionId;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
class JpaTransactionPersistenceAdapter implements TransactionRepositoryPort {

    private final SpringDataTransactionRepository repository;
    private final WalletPersistenceMapper mapper;

    JpaTransactionPersistenceAdapter(SpringDataTransactionRepository repository, WalletPersistenceMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Transaction> findById(TransactionId id) {
        return repository.findById(id.value()).map(mapper::toDomain);
    }

    @Override
    public Transaction save(Transaction transaction) {
        return mapper.toDomain(repository.save(mapper.toEntity(transaction)));
    }
}
