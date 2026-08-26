package com.wallet.app.wallet.adapter.in.web;

public record TransferErrorResponse(
  String code,
  String message
) {
}
