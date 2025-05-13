package com.antharos.corporateorganization.application.queries.employee;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindEmployeeByIdQueryHandlerUnitTest {

  private UserRepository userRepository;
  private FindEmployeeByIdQueryHandler queryHandler;

  @BeforeEach
  void setUp() {
    this.userRepository = mock(UserRepository.class);
    this.queryHandler = new FindEmployeeByIdQueryHandler(this.userRepository);
  }

  @Test
  void whenHandleIsCalled_thenReturnsEmployeeById() {
    EmployeeId employeeId = EmployeeId.of(UUID.randomUUID().toString());
    Employee mockEmployee = new Employee(employeeId);
    FindEmployeeByIdQuery query =
        FindEmployeeByIdQuery.builder().id(employeeId.getValueAsString()).build();

    when(this.userRepository.findById(employeeId.getValueAsString()))
        .thenReturn(Optional.of(mockEmployee));

    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.of(mockEmployee), result);
    verify(this.userRepository, times(1)).findById(employeeId.getValueAsString());
  }

  @Test
  void whenEmployeeNotFound_thenReturnsEmptyOptional() {
    EmployeeId employeeId = EmployeeId.of(UUID.randomUUID().toString());
    FindEmployeeByIdQuery query = new FindEmployeeByIdQuery(employeeId.getValueAsString());

    when(this.userRepository.findById(employeeId.getValueAsString())).thenReturn(Optional.empty());

    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.empty(), result);
    verify(this.userRepository, times(1)).findById(employeeId.getValueAsString());
  }
}
