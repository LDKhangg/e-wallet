package com.wallet.app.wallet.adapter.in.web;

import java.util.UUID;

public record DepositResponse(UUID transactionId, String message) {}
