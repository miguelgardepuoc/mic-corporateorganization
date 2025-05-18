package com.antharos.corporateorganization.application.commands.employee.putonleave;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PutEmployeeOnLeaveCommandHandlerUnitTest {

  private EmployeeRepository employeeRepository;
  private EventProducer eventProducer;
  private PutEmployeeOnLeaveCommandHandler handler;

  @BeforeEach
  void setUp() {
    employeeRepository = mock(EmployeeRepository.class);
    eventProducer = mock(EventProducer.class);
    handler = new PutEmployeeOnLeaveCommandHandler(employeeRepository, eventProducer);
  }

  @Test
  void whenEmployeeNotFound_thenThrowsException() {
    String employeeId = UUID.randomUUID().toString();
    PutEmployeeOnLeaveCommand command = new PutEmployeeOnLeaveCommand(employeeId, "admin");

    when(employeeRepository.findBy(EmployeeId.of(employeeId))).thenReturn(Optional.empty());

    assertThrows(EmployeeNotFoundException.class, () -> handler.doHandle(command));

    verify(employeeRepository, times(1)).findBy(EmployeeId.of(employeeId));
    verifyNoMoreInteractions(employeeRepository);
    verifyNoInteractions(eventProducer);
  }

  @Test
  void whenEmployeeFound_thenPutOnLeaveAndSaveAndSendEvent() {
    String employeeId = UUID.randomUUID().toString();
    String admin = "admin";
    PutEmployeeOnLeaveCommand command = new PutEmployeeOnLeaveCommand(employeeId, admin);

    Employee employee = mock(Employee.class);
    when(employeeRepository.findBy(EmployeeId.of(employeeId))).thenReturn(Optional.of(employee));

    handler.doHandle(command);

    verify(employee, times(1)).putOnLeave(admin);
    verify(employeeRepository, times(1)).save(employee);
    verify(eventProducer, times(1)).sendEmployeePutOnLeaveEvent(employee);
  }
}
