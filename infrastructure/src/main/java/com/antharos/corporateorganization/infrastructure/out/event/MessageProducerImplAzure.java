package com.antharos.corporateorganization.infrastructure.out.event;

import com.antharos.corporateorganization.infrastructure.out.event.model.BaseEvent;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayload;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayloadMapper;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("prod")
@Service
@Slf4j
public class MessageProducerImplAzure extends AbstractMessageProducer {

  private final ServiceBusSenderClient senderClient;
  private final ObjectMapper objectMapper;

  public MessageProducerImplAzure(
      @Value("${producer.event.connection-string}") String connectionString,
      @Value("${producer.topic.name}") String topicName,
      ObjectMapper objectMapper,
      EmployeePayloadMapper mapper) {
    super(mapper);
    this.objectMapper = objectMapper;
    this.senderClient =
        new ServiceBusClientBuilder()
            .connectionString(connectionString)
            .sender()
            .topicName(topicName)
            .buildClient();
  }

  @Override
  public void sendMessage(BaseEvent<EmployeePayload> event) {
    try {
      String messageJson = objectMapper.writeValueAsString(event);
      senderClient.sendMessage(new ServiceBusMessage(messageJson));
      log.info("Message sent (Azure): {}", messageJson);
    } catch (Exception e) {
      log.error("Error sending message (Azure): {}", event, e);
    }
  }
}
