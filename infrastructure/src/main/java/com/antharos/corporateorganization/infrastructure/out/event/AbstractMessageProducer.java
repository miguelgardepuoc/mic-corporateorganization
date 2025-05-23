package com.antharos.corporateorganization.infrastructure.out.event;

import static com.antharos.corporateorganization.infrastructure.out.event.model.EventNames.*;
import static com.antharos.corporateorganization.infrastructure.out.event.model.EventNames.EMPLOYEE_MARKED_AS_INACTIVE;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.infrastructure.out.event.model.BaseEvent;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayload;
import com.antharos.corporateorganization.infrastructure.out.event.model.EmployeePayloadMapper;
import java.time.Instant;
import java.util.UUID;

public abstract class AbstractMessageProducer implements EventProducer {

  private static final String EMPLOYEE_AGGREGATE = "Employee";
  private final EmployeePayloadMapper mapper;

  public AbstractMessageProducer(EmployeePayloadMapper mapper) {
    this.mapper = mapper;
  }

  @Override
  public void sendEmployeeHiredEvent(Employee employee) {
    BaseEvent<EmployeePayload> event =
        new BaseEvent<>(
            UUID.randomUUID().toString(),
            Instant.now(),
            EMPLOYEE_HIRED.getDescription(),
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
            EMPLOYEE_ON_LEAVE.getDescription(),
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
            EMPLOYEE_TERMINATED.getDescription(),
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
            EMPLOYEE_MARKED_AS_INACTIVE.getDescription(),
            employee.getId().getValueAsString(),
            EMPLOYEE_AGGREGATE,
            employee.getLastModifiedBy(),
            1,
            this.mapper.toPayload(employee));
    this.sendMessage(event);
  }

  public abstract void sendMessage(BaseEvent<EmployeePayload> event);
}
