package com.wallet.app.wallet.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.app.wallet.application.port.in.DepositCommand;
import com.wallet.app.wallet.application.port.in.DepositMoneyUseCase;
import com.wallet.app.wallet.domain.TransactionId;
import java.math.BigDecimal;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(DepositController.class)
public class DepositControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private DepositMoneyUseCase depositMoneyUseCase;

  @Test
  void shouldDepositSuccessfully() throws Exception {
    UUID walletId = UUID.randomUUID();
    UUID txId = UUID.randomUUID();

    given(depositMoneyUseCase.deposit(any(DepositCommand.class)))
        .willReturn(new TransactionId(txId));

    DepositRequest request = new DepositRequest(walletId, BigDecimal.valueOf(500), "MOMO");

    mockMvc
        .perform(
            post("/api/wallets/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.transactionId").value(txId.toString()))
        .andExpect(jsonPath("$.message").value("Deposit successful"));
  }
}
