package com.wallet.app.wallet.adapter.in.web;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

public record DepositRequest(
    @NotNull(message = "Wallet ID must not be null") UUID walletId,
    @NotNull(message = "Amount must not be null")
        @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
        BigDecimal amount,
    @NotBlank(message = "Method code must not be blank") String methodCode) {}
