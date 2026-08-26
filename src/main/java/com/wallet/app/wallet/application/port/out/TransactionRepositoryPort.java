package com.wallet.app.wallet.application.port.out;

import com.wallet.app.wallet.domain.Transaction;
import com.wallet.app.wallet.domain.TransactionId;
import java.util.Optional;

public interface TransactionRepositoryPort {

    Optional<Transaction> findById(TransactionId id);

    Transaction save(Transaction transaction);
}
