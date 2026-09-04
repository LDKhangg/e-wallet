package com.wallet.app.wallet.adapter.in.web;

import com.wallet.app.wallet.application.port.in.DepositCommand;
import com.wallet.app.wallet.application.port.in.DepositMoneyUseCase;
import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.TransactionId;
import com.wallet.app.wallet.domain.WalletId;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class DepositController {

  private final DepositMoneyUseCase depositMoneyUseCase;

  @PostMapping("/deposit")
  public ResponseEntity<DepositResponse> deposit(@Valid @RequestBody DepositRequest request) {
    DepositCommand command =
        new DepositCommand(
            new WalletId(request.walletId()),
            Money.of(request.amount()),
            request.methodCode());

    TransactionId transactionId = depositMoneyUseCase.deposit(command);

    DepositResponse response =
        new DepositResponse(transactionId.value(), "Deposit successful");
    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }
}
