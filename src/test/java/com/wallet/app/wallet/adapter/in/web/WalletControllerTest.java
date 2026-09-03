package com.wallet.app.wallet.adapter.in.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wallet.app.wallet.application.port.in.CreateWalletCommand;
import com.wallet.app.wallet.application.port.in.CreateWalletUseCase;
import com.wallet.app.wallet.domain.WalletId;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(WalletController.class)
public class WalletControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private CreateWalletUseCase createWalletUseCase;

  @Test
  void shouldCreateWalletSuccessfully() throws Exception {
    UUID userId = UUID.randomUUID();
    UUID walletId = UUID.randomUUID();

    given(createWalletUseCase.create(any(CreateWalletCommand.class)))
        .willReturn(new WalletId(walletId));

    CreateWalletRequest request = new CreateWalletRequest(userId);

    mockMvc
        .perform(
            post("/wallets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.walletId").value(walletId.toString()));
  }
}
