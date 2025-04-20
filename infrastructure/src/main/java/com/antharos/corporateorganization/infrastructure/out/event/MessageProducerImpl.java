package com.antharos.corporateorganization.infrastructure.out.event;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.repository.MessageProducer;
import com.antharos.corporateorganization.infrastructure.out.event.model.UserDomainMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Topic;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessageProducerImpl implements MessageProducer {

  private final ConnectionFactory producerConnectionFactory;
  private final ObjectMapper objectMapper;

  @Value("${producer.topic.name}")
  private String topicName;

  public MessageProducerImpl(
      ConnectionFactory producerConnectionFactory, ObjectMapper objectMapper) {
    this.producerConnectionFactory = producerConnectionFactory;
    this.objectMapper = objectMapper;
  }

  public void sendMessage(final UUID id, final String subject, final User user) {
    try (JMSContext context = this.producerConnectionFactory.createContext()) {
      final Topic topic = context.createTopic(this.topicName);

      final UserDomainMessage message = new UserDomainMessage(id.toString(), subject, user);

      final String messageJson = this.objectMapper.writeValueAsString(message);

      context.createProducer().send(topic, messageJson);

      log.info("Message sent: {}", messageJson);
    } catch (Exception e) {
      log.error("Error sending message: {} ", user, e);
    }
  }

  @Override
  public void sendUserHiredMessage(User user) {
    this.sendMessage(UUID.fromString(user.getId().getValueAsString()), "USER_HIRED", user);
  }

  @Override
  public void sendUserPutOnLeaveEvent(User user) {
    this.sendMessage(UUID.fromString(user.getId().getValueAsString()), "USER_ON_LEAVE", user);
  }

  @Override
  public void sendUserTerminatedEvent(User user) {
    this.sendMessage(UUID.fromString(user.getId().getValueAsString()), "USER_TERMINATED", user);
  }

  @Override
  public void sendUserMarkedAsInactiveEvent(User user) {
    this.sendMessage(UUID.fromString(user.getId().getValueAsString()), "USER_MARKED_AS_INACTIVE", user);
  }
}
