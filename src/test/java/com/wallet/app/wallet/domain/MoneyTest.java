package com.wallet.app.wallet.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class MoneyTest {

    @Test
    void accepts_non_negative_whole_vnd_amounts() {
        Money money = Money.of(new BigDecimal("1000"));

        assertThat(money.amount()).isEqualByComparingTo("1000");
    }

    @Test
    void rejects_fractional_vnd() {
        assertThatThrownBy(() -> Money.of(new BigDecimal("1.5")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void rejects_negative_vnd() {
        assertThatThrownBy(() -> Money.of(-1))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
