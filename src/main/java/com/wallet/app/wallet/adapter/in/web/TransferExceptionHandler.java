package com.wallet.app.wallet.adapter.in.web;

import com.wallet.app.wallet.application.port.in.WalletAlreadyExistsException;
import com.wallet.app.wallet.application.port.in.WalletNotFoundException;
import com.wallet.app.wallet.domain.InsufficientFundsException;
import org.springframework.http.HttpStatus;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TransferExceptionHandler {

  @ExceptionHandler(WalletAlreadyExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public TransferErrorResponse handleWalletAlreadyExists(WalletAlreadyExistsException exception) {
    return new TransferErrorResponse("WALLET_ALREADY_EXISTS", exception.getMessage());
  }

  @ExceptionHandler(WalletNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public TransferErrorResponse handleWalletNotFound(WalletNotFoundException exception) {
    return new TransferErrorResponse("WALLET_NOT_FOUND", exception.getMessage());
  }

  @ExceptionHandler(InsufficientFundsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public TransferErrorResponse handleInsufficientFunds(InsufficientFundsException exception) {
    return new TransferErrorResponse("INSUFFICIENT_FUNDS", exception.getMessage());
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public TransferErrorResponse handleInvalidTransfer(IllegalArgumentException exception) {
    return new TransferErrorResponse("INVALID_TRANSFER", exception.getMessage());
  }

  @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  public TransferErrorResponse handleConcurrentModification(
      ObjectOptimisticLockingFailureException exception) {
    return new TransferErrorResponse(
        "CONCURRENT_MODIFICATION", "wallet was modified concurrently; retry the transfer");
  }
}
