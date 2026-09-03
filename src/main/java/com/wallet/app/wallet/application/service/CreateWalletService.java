package com.wallet.app.wallet.application.service;

import com.wallet.app.wallet.application.port.in.CreateWalletCommand;
import com.wallet.app.wallet.application.port.in.CreateWalletUseCase;
import com.wallet.app.wallet.application.port.in.WalletAlreadyExistsException;
import com.wallet.app.wallet.application.port.out.WalletRepositoryPort;
import com.wallet.app.wallet.domain.Wallet;
import com.wallet.app.wallet.domain.WalletId;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateWalletService implements CreateWalletUseCase {
  private final WalletRepositoryPort walletRepositoryPort;

  @Override
  @Transactional
  public WalletId create(CreateWalletCommand command) {
    Objects.requireNonNull(command, "command must not be null");

    if (walletRepositoryPort.findByUserId(command.userId()).isPresent()) {
      throw new WalletAlreadyExistsException(command.userId());
    }
    Wallet newWallet = Wallet.create(command.userId());
    var savedWallet = walletRepositoryPort.save(newWallet);
    return savedWallet.id();
  }
}
