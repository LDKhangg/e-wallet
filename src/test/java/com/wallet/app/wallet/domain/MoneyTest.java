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
  void accepts_maximum_persistable_vnd_amount() {
    Money money = Money.of(new BigDecimal("9999999999999999999"));

    assertThat(money.amount()).isEqualByComparingTo("9999999999999999999");
  }

  @Test
  void rejects_vnd_amount_above_persistable_maximum() {
    assertThatThrownBy(() -> Money.of(new BigDecimal("10000000000000000000")))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rejects_fractional_vnd() {
    assertThatThrownBy(() -> Money.of(new BigDecimal("1.5")))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void rejects_negative_vnd() {
    assertThatThrownBy(() -> Money.of(-1)).isInstanceOf(IllegalArgumentException.class);
  }
}
