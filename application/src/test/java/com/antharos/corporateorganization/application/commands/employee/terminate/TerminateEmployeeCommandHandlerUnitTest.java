package com.antharos.corporateorganization.application.commands.employee.terminate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TerminateEmployeeCommandHandlerUnitTest {

  private UserRepository userRepository;
  private EventProducer eventProducer;
  private TerminateEmployeeCommandHandler handler;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    eventProducer = mock(EventProducer.class);
    handler = new TerminateEmployeeCommandHandler(userRepository, eventProducer);
  }

  @Test
  void whenEmployeeNotFound_thenThrowsException() {
    String employeeId = UUID.randomUUID().toString();
    TerminateEmployeeCommand command = new TerminateEmployeeCommand(employeeId, "admin");

    when(userRepository.findBy(EmployeeId.of(employeeId))).thenReturn(Optional.empty());

    assertThrows(EmployeeNotFoundException.class, () -> handler.doHandle(command));

    verify(userRepository, times(1)).findBy(EmployeeId.of(employeeId));
    verifyNoMoreInteractions(userRepository);
    verifyNoInteractions(eventProducer);
  }

  @Test
  void whenEmployeeFound_thenTerminateAndSendEvent() {
    String employeeId = UUID.randomUUID().toString();
    String admin = "admin";
    TerminateEmployeeCommand command = new TerminateEmployeeCommand(employeeId, admin);

    Employee employee = mock(Employee.class);
    when(userRepository.findBy(EmployeeId.of(employeeId))).thenReturn(Optional.of(employee));

    handler.doHandle(command);

    verify(employee, times(1)).terminate(admin);
    verify(userRepository, times(1)).save(employee);
    verify(eventProducer, times(1)).sendEmployeeTerminatedEvent(employee);
  }
}
