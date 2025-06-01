package com.antharos.corporateorganization.application.queries.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.Status;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindEmployeeByUsernameQueryHandlerUnitTest {

  private EmployeeRepository employeeRepository;
  private FindEmployeeByUsernameQueryHandler queryHandler;

  @BeforeEach
  void setUp() {
    this.employeeRepository = mock(EmployeeRepository.class);
    this.queryHandler = new FindEmployeeByUsernameQueryHandler(this.employeeRepository);
  }

  @Test
  void whenHandleIsCalledAndEmployeeIsActive_thenReturnsEmployee() {
    String username = "testUser";
    Employee mockEmployee = mock(Employee.class);

    when(mockEmployee.getStatus()).thenReturn(Status.ACTIVE);
    when(this.employeeRepository.findByUsername(username)).thenReturn(Optional.of(mockEmployee));

    FindEmployeeByUsernameQuery query = new FindEmployeeByUsernameQuery(username);
    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.of(mockEmployee), result);
    verify(this.employeeRepository, times(1)).findByUsername(username);
    verify(mockEmployee, times(1)).getStatus();
  }

  @Test
  void whenHandleIsCalledAndEmployeeIsNotActive_thenReturnsEmptyOptional() {
    String username = "inactiveUser";
    Employee mockEmployee = mock(Employee.class);

    when(mockEmployee.getStatus()).thenReturn(Status.INACTIVE);
    when(this.employeeRepository.findByUsername(username)).thenReturn(Optional.of(mockEmployee));

    FindEmployeeByUsernameQuery query = new FindEmployeeByUsernameQuery(username);
    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.empty(), result);
    verify(this.employeeRepository, times(1)).findByUsername(username);
    verify(mockEmployee, times(1)).getStatus();
  }

  @Test
  void whenEmployeeNotFound_thenReturnsEmptyOptional() {
    String username = "nonExistentUser";
    FindEmployeeByUsernameQuery query = new FindEmployeeByUsernameQuery(username);

    when(this.employeeRepository.findByUsername(username)).thenReturn(Optional.empty());

    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.empty(), result);
    verify(this.employeeRepository, times(1)).findByUsername(username);
  }
}
