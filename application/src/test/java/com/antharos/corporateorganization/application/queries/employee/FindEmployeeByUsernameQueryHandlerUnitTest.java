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

class FindEmployeeByUsernameQueryHandlerUnitTest {

  private UserRepository userRepository;
  private FindEmployeeByUsernameQueryHandler queryHandler;

  @BeforeEach
  void setUp() {
    this.userRepository = mock(UserRepository.class);
    this.queryHandler = new FindEmployeeByUsernameQueryHandler(this.userRepository);
  }

  @Test
  void whenHandleIsCalled_thenReturnsEmployeeByUsername() {
    String username = "testUser";
    Employee mockEmployee = new Employee(EmployeeId.of(UUID.randomUUID().toString()));
    FindEmployeeByUsernameQuery query = new FindEmployeeByUsernameQuery(username);

    when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(mockEmployee));

    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.of(mockEmployee), result);
    verify(this.userRepository, times(1)).findByUsername(username);
  }

  @Test
  void whenEmployeeNotFound_thenReturnsEmptyOptional() {
    String username = "nonExistentUser";
    FindEmployeeByUsernameQuery query = new FindEmployeeByUsernameQuery(username);

    when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());

    Optional<Employee> result = this.queryHandler.handle(query);

    assertEquals(Optional.empty(), result);
    verify(this.userRepository, times(1)).findByUsername(username);
  }
}
