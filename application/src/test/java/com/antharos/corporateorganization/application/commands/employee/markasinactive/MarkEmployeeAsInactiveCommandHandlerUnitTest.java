package com.antharos.corporateorganization.application.commands.employee.markasinactive;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.employee.*;
import com.antharos.corporateorganization.domain.employee.event.EmployeeMarkedAsInactiveEvent;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MarkEmployeeAsInactiveCommandHandlerUnitTest {

  private EmployeeRepository employeeRepository;
  private EventProducer eventProducer;
  private MarkEmployeeAsInactiveCommandHandler commandHandler;

  @BeforeEach
  void setUp() {
    this.employeeRepository = mock(EmployeeRepository.class);
    this.eventProducer = mock(EventProducer.class);
    this.commandHandler =
        new MarkEmployeeAsInactiveCommandHandler(employeeRepository, eventProducer);
  }

  @Test
  void whenEmployeeNotFound_thenThrowsEmployeeNotFoundException() {
    String userId = UUID.randomUUID().toString();
    MarkEmployeeAsInactiveCommand command = new MarkEmployeeAsInactiveCommand(userId, "admin");

    when(employeeRepository.findBy(EmployeeId.of(userId))).thenReturn(Optional.empty());

    assertThrows(EmployeeNotFoundException.class, () -> commandHandler.doHandle(command));

    verify(employeeRepository, times(1)).findBy(EmployeeId.of(userId));
    verifyNoMoreInteractions(employeeRepository);
    verifyNoInteractions(eventProducer);
  }

  @Test
  void whenEmployeeFound_thenMarksAsInactiveAndPublishesEvent() {
    String userId = UUID.randomUUID().toString();
    String adminUser = "admin";

    EmployeeId employeeId = EmployeeId.of(userId);
    MarkEmployeeAsInactiveCommand command = new MarkEmployeeAsInactiveCommand(userId, adminUser);

    Employee employee = mock(Employee.class);
    EmployeeMarkedAsInactiveEvent event = new EmployeeMarkedAsInactiveEvent(employee);

    when(employeeRepository.findBy(employeeId)).thenReturn(Optional.of(employee));
    when(employee.getDomainEvents()).thenReturn(List.of(event));

    commandHandler.doHandle(command);

    verify(employee, times(1)).markAsInactive(adminUser, eventProducer);
    verify(employeeRepository, times(1)).save(employee);

    verify(eventProducer, times(1)).sendEmployeeMarkedAsInactiveEvent(employee);

    verify(employee, times(1)).clearDomainEvents();
  }

  @Test
  void whenNoDomainEvents_thenDoesNotSendEvent() {
    String userId = UUID.randomUUID().toString();
    String adminUser = "admin";

    EmployeeId employeeId = EmployeeId.of(userId);
    MarkEmployeeAsInactiveCommand command = new MarkEmployeeAsInactiveCommand(userId, adminUser);

    Employee employee = mock(Employee.class);

    when(employeeRepository.findBy(employeeId)).thenReturn(Optional.of(employee));
    when(employee.getDomainEvents()).thenReturn(Collections.emptyList());

    commandHandler.doHandle(command);

    verify(employee, times(1)).markAsInactive(adminUser, eventProducer);
    verify(employeeRepository, times(1)).save(employee);
    verify(employee, times(1)).getDomainEvents();
    verify(employee, times(1)).clearDomainEvents();

    verify(eventProducer, never()).sendEmployeeMarkedAsInactiveEvent(any());
  }
}
