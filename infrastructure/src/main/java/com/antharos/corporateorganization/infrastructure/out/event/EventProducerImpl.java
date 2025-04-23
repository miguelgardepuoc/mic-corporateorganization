package com.antharos.corporateorganization.infrastructure.out.event;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.infrastructure.out.event.model.BaseEvent;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayload;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayloadMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSContext;
import jakarta.jms.Topic;
import java.time.Instant;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EventProducerImpl implements EventProducer {

  private final ConnectionFactory producerConnectionFactory;
  private final ObjectMapper objectMapper;
  private static final String EMPLOYEE_AGGREGATE = "Employee";
  private final EmployeePayloadMapper mapper;

  @Value("${producer.topic.name}")
  private String topicName;

  public EventProducerImpl(
      ConnectionFactory producerConnectionFactory,
      ObjectMapper objectMapper,
      EmployeePayloadMapper mapper) {
    this.producerConnectionFactory = producerConnectionFactory;
    this.objectMapper = objectMapper;
    this.mapper = mapper;
  }

  public void sendMessage(final BaseEvent<EmployeePayload> event) {
    try (JMSContext context = this.producerConnectionFactory.createContext()) {
      final Topic topic = context.createTopic(this.topicName);

      final String messageJson = this.objectMapper.writeValueAsString(event);

      context.createProducer().send(topic, messageJson);

      log.info("Message sent: {}", messageJson);
    } catch (Exception e) {
      log.error("Error sending message: {} ", event, e);
    }
  }

  @Override
  public void sendEmployeeHiredEvent(Employee employee) {
    BaseEvent<EmployeePayload> event =
        new BaseEvent<>(
            UUID.randomUUID().toString(),
            Instant.now(),
            "EmployeeHired",
            employee.getId().getValueAsString(),
                EMPLOYEE_AGGREGATE,
            employee.getCreatedBy(),
            1,
            this.mapper.toPayload(employee));
    this.sendMessage(event);
  }

  @Override
  public void sendEmployeePutOnLeaveEvent(Employee employee) {
    BaseEvent<EmployeePayload> event =
        new BaseEvent<>(
            UUID.randomUUID().toString(),
            Instant.now(),
            "EmployeeOnLeave",
            employee.getId().getValueAsString(),
                EMPLOYEE_AGGREGATE,
            employee.getLastModifiedBy(),
            1,
            this.mapper.toPayload(employee));
    this.sendMessage(event);
  }

  @Override
  public void sendEmployeeTerminatedEvent(Employee employee) {
    BaseEvent<EmployeePayload> event =
        new BaseEvent<>(
            UUID.randomUUID().toString(),
            Instant.now(),
            "EmployeeTerminated",
            employee.getId().getValueAsString(),
                EMPLOYEE_AGGREGATE,
            employee.getLastModifiedBy(),
            1,
            this.mapper.toPayload(employee));
    this.sendMessage(event);
  }

  @Override
  public void sendEmployeeMarkedAsInactiveEvent(Employee employee) {
    BaseEvent<EmployeePayload> event =
        new BaseEvent<>(
            UUID.randomUUID().toString(),
            Instant.now(),
            "EmployeeMarkedAsInactive",
            employee.getId().getValueAsString(),
                EMPLOYEE_AGGREGATE,
            employee.getLastModifiedBy(),
            1,
            this.mapper.toPayload(employee));
    this.sendMessage(event);
  }
}
