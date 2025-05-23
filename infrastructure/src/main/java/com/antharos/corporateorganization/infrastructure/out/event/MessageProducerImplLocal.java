package com.antharos.corporateorganization.infrastructure.out.event;

import com.antharos.corporateorganization.infrastructure.out.event.model.BaseEvent;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayload;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayloadMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Topic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("!prod")
@Service
@Slf4j
public class MessageProducerImplLocal extends AbstractMessageProducer {

  private final ConnectionFactory producerConnectionFactory;
  private final ObjectMapper objectMapper;

  @Value("${producer.topic.name}")
  private String topicName;

  public MessageProducerImplLocal(
      ConnectionFactory producerConnectionFactory,
      ObjectMapper objectMapper,
      EmployeePayloadMapper mapper) {
    super(mapper);
    this.producerConnectionFactory = producerConnectionFactory;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(final BaseEvent<EmployeePayload> event) {
    try (JMSContext context = this.producerConnectionFactory.createContext()) {
      final Topic topic = context.createTopic(this.topicName);
      final String messageJson = this.objectMapper.writeValueAsString(event);
      context.createProducer().send(topic, messageJson);
      log.info("Message sent (local): {}", messageJson);
    } catch (Exception e) {
      log.error("Error sending message (local): {}", event, e);
    }
  }
}
