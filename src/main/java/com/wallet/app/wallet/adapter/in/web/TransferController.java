package com.wallet.app.wallet.adapter.in.web;

import com.wallet.app.wallet.application.port.in.TransferMoneyCommand;
import com.wallet.app.wallet.application.port.in.TransferMoneyUseCase;
import com.wallet.app.wallet.domain.Money;
import com.wallet.app.wallet.domain.TransactionId;
import com.wallet.app.wallet.domain.WalletId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transfers")
public class TransferController {
  private final TransferMoneyUseCase transferMoneyUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public TransferResponse transfer(@RequestBody TransferRequest request) {
    TransferMoneyCommand command =
        new TransferMoneyCommand(
            new WalletId(request.sourceWalletId()),
            new WalletId(request.destinationWalletId()),
            Money.of(request.amount()));
    TransactionId transactionId = transferMoneyUseCase.transfer(command);
    return new TransferResponse(transactionId.value());
  }
}
