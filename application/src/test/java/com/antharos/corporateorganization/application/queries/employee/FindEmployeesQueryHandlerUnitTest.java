package com.antharos.corporateorganization.application.queries.employee;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_DEPARTMENT_HEAD;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.UsernameNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FindEmployeesQueryHandlerUnitTest {

  private UserRepository userRepository;
  private FindEmployeesQueryHandler queryHandler;

  @BeforeEach
  void setUp() {
    this.userRepository = mock(UserRepository.class);
    this.queryHandler = new FindEmployeesQueryHandler(this.userRepository);
  }

  @Test
  void whenHandleIsCalled_andUserIsDepartmentHead_thenReturnsEmployeesFromDepartment() {
    String username = "departmentHead";
    Employee departmentHead =
        Employee.builder()
            .id(EmployeeId.of(UUID.randomUUID().toString()))
            .role(ROLE_DEPARTMENT_HEAD)
            .department(new Department(DepartmentId.of(UUID.randomUUID().toString())))
            .build();
    DepartmentId departmentId = departmentHead.getDepartment().getId();
    Employee mockEmployee1 = new Employee(EmployeeId.of(UUID.randomUUID().toString()));
    Employee mockEmployee2 = new Employee(EmployeeId.of(UUID.randomUUID().toString()));

    FindEmployeesQuery query = new FindEmployeesQuery(username);

    when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(departmentHead));
    when(this.userRepository.findByDepartmentId(departmentId))
        .thenReturn(Arrays.asList(mockEmployee1, mockEmployee2));

    List<Employee> result = this.queryHandler.handle(query);

    assertEquals(2, result.size());
    verify(this.userRepository, times(1)).findByUsername(username);
    verify(this.userRepository, times(1)).findByDepartmentId(departmentId);
  }

  @Test
  void whenHandleIsCalled_andUserIsNotDepartmentHead_thenReturnsAllEmployees() {
    String username = "nonDepartmentHead";
    Employee nonDepartmentHead = new Employee(EmployeeId.of(UUID.randomUUID().toString()));
    Employee mockEmployee1 = new Employee(EmployeeId.of(UUID.randomUUID().toString()));
    Employee mockEmployee2 = new Employee(EmployeeId.of(UUID.randomUUID().toString()));

    FindEmployeesQuery query = new FindEmployeesQuery(username);

    when(this.userRepository.findByUsername(username)).thenReturn(Optional.of(nonDepartmentHead));
    when(this.userRepository.findAll()).thenReturn(Arrays.asList(mockEmployee1, mockEmployee2));

    List<Employee> result = this.queryHandler.handle(query);

    assertEquals(2, result.size());
    verify(this.userRepository, times(1)).findByUsername(username);
    verify(this.userRepository, times(1)).findAll();
  }

  @Test
  void whenUsernameNotFound_thenThrowsUsernameNotFoundException() {
    String username = "nonExistentUser";
    FindEmployeesQuery query = new FindEmployeesQuery(username);

    when(this.userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> this.queryHandler.handle(query));

    verify(this.userRepository, times(1)).findByUsername(username);
  }
}
