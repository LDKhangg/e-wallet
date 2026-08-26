package com.wallet.app.wallet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

class WalletTest {

    @Test
    void restores_wallet_with_its_values() {
        WalletId walletId = WalletId.newId();
        UserId userId = UserId.newId();
        Money balance = Money.of(1000);

        Wallet wallet = Wallet.restore(walletId, userId, balance, 3);

        assertThat(wallet)
                .isEqualTo(Wallet.restore(walletId, userId, balance, 3));
        assertThat(wallet.id()).isEqualTo(walletId);
        assertThat(wallet.userId()).isEqualTo(userId);
        assertThat(wallet.balance()).isEqualTo(balance);
        assertThat(wallet.version()).isEqualTo(3);
    }

    @Test
    void rejects_negative_wallet_balance() {
        assertThatThrownBy(() -> Wallet.restore(WalletId.newId(), UserId.newId(), Money.of(-1), 0))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejects_negative_wallet_version() {
        assertThatThrownBy(() -> Wallet.restore(WalletId.newId(), UserId.newId(), Money.of(0), -1))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void restores_non_zero_transaction_with_its_values() {
        TransactionId transactionId = TransactionId.newId();
        WalletId sourceWalletId = WalletId.newId();
        WalletId destinationWalletId = WalletId.newId();
        Money amount = Money.of(1000);

        Transaction transaction = Transaction.restore(
                transactionId, sourceWalletId, destinationWalletId, amount, TransactionStatus.PENDING);

        assertThat(transaction).isEqualTo(Transaction.restore(
                transactionId, sourceWalletId, destinationWalletId, amount, TransactionStatus.PENDING));
        assertThat(transaction.id()).isEqualTo(transactionId);
        assertThat(transaction.sourceWalletId()).isEqualTo(sourceWalletId);
        assertThat(transaction.destinationWalletId()).isEqualTo(destinationWalletId);
        assertThat(transaction.amount()).isEqualTo(amount);
        assertThat(transaction.status()).isEqualTo(TransactionStatus.PENDING);
    }

    @Test
    void rejects_zero_transaction_amount() {
        assertThatThrownBy(() -> Transaction.restore(
                TransactionId.newId(),
                WalletId.newId(),
                WalletId.newId(),
                Money.of(0),
                TransactionStatus.PENDING))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
