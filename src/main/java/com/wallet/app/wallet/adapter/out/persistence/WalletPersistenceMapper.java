package com.wallet.app.wallet.adapter.out.persistence;

import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.Transaction;
import com.wallet.app.wallet.domain.TransactionId;
import com.wallet.app.wallet.domain.UserId;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import org.springframework.stereotype.Component;

@Component
class WalletPersistenceMapper {

    WalletJpaEntity toEntity(Wallet wallet) {
        return new WalletJpaEntity(
                wallet.id().value(), wallet.userId().value(), wallet.balance().amount(), wallet.version());
    }

    Wallet toDomain(WalletJpaEntity wallet) {
        return Wallet.restore(
                new WalletId(wallet.id()),
                new UserId(wallet.userId()),
                Money.of(wallet.balance()),
                wallet.version());
    }

    TransactionJpaEntity toEntity(Transaction transaction) {
        return new TransactionJpaEntity(
                transaction.id().value(),
                transaction.sourceWalletId().value(),
                transaction.destinationWalletId().value(),
                transaction.amount().amount(),
                transaction.status());
    }

    Transaction toDomain(TransactionJpaEntity transaction) {
        return Transaction.restore(
                new TransactionId(transaction.id()),
                new WalletId(transaction.sourceWalletId()),
                new WalletId(transaction.destinationWalletId()),
                Money.of(transaction.amount()),
                transaction.status());
    }
}
