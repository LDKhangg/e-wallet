package com.wallet.app.wallet.adapter.in.web;

import com.wallet.app.wallet.application.port.in.CreateWalletCommand;
import com.wallet.app.wallet.application.port.in.CreateWalletUseCase;
import com.wallet.app.wallet.domain.UserId;
import com.wallet.app.wallet.domain.WalletId;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/wallets")
public class WalletController {

  private final CreateWalletUseCase createWalletUseCase;

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public CreateWalletResponse create(@RequestBody CreateWalletRequest request) {
    WalletId walletId =
        createWalletUseCase.create(new CreateWalletCommand(new UserId(request.userId())));
    return new CreateWalletResponse(walletId.value());
  }
}
