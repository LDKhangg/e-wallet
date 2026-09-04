package com.wallet.app.wallet.adapter.out.messaging;

import com.wallet.app.wallet.application.port.out.EventPublisherPort;
import com.wallet.app.wallet.domain.event.MoneyTransferredEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMqEventPublisher implements EventPublisherPort {

  private final RabbitTemplate rabbitTemplate;

  private static final String EXCHANGE = "wallet.exchange";
  private static final String ROUTING_KEY = "wallet.transfer";

  @Override
  public void publishMoneyTransferred(MoneyTransferredEvent event) {
    log.info("Publishing MoneyTransferredEvent to RabbitMQ: {}",event);
    rabbitTemplate.convertAndSend(EXCHANGE,ROUTING_KEY,event);
  }
}
