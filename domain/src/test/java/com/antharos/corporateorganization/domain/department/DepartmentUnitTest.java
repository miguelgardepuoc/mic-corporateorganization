package com.antharos.corporateorganization.domain.department;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_DEPARTMENT_HEAD;
import static com.antharos.corporateorganization.domain.employee.Role.ROLE_EMPLOYEE;
import static com.antharos.corporateorganization.domain.employee.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.exception.NotActiveUserException;
import com.antharos.corporateorganization.domain.department.exception.NotEmployeeException;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.globalexceptions.ConflictException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartmentUnitTest {

  private DepartmentId departmentId;
  private Department department;
  private final String createdBy = "admin";
  private final String modifier = "modifier";

  @BeforeEach
  void setUp() {
    departmentId = DepartmentId.of(UUID.randomUUID().toString());
    department = Department.create(departmentId, "Initial Desc", createdBy);
  }

  @Test
  void whenRenameOnActiveDepartment_thenDescriptionIsUpdated() {
    department.rename("New Description", modifier);
    assertEquals("New Description", department.getDescription());
    assertEquals(modifier, department.getLastModifiedBy());
  }

  @Test
  void whenRenameOnInactiveDepartment_thenThrowConflictException() {
    department.remove(modifier);
    assertThrows(ConflictException.class, () -> department.rename("Name", modifier));
  }

  @Test
  void whenRemoveOnActiveDepartment_thenIsActiveBecomesFalse() {
    department.remove(modifier);
    assertFalse(department.isActive());
    assertEquals(modifier, department.getLastModifiedBy());
  }

  @Test
  void whenRemoveOnInactiveDepartment_thenThrowConflictException() {
    department.remove(modifier);
    assertThrows(ConflictException.class, () -> department.remove(modifier));
  }

  @Test
  void whenUpdateDepartmentHeadWithInactiveDepartment_thenThrowConflictException() {
    department.remove(modifier);

    Employee employee = mock(Employee.class);
    UserRepository userRepository = mock(UserRepository.class);

    assertThrows(
        ConflictException.class,
        () -> department.updateDepartmentHead(employee, modifier, userRepository));
  }

  @Test
  void whenUpdateDepartmentHeadWithInactiveEmployee_thenThrowNotActiveUserException() {
    Employee employee = mock(Employee.class);
    UserRepository userRepository = mock(UserRepository.class);

    assertThrows(
        NotActiveUserException.class,
        () -> department.updateDepartmentHead(employee, modifier, userRepository));
  }

  @Test
  void whenNotEmployeeRole_thenThrowNotEmployeeException() {
    Employee employee = mock(Employee.class);
    when(employee.getUsername()).thenReturn("oldHead");
    when(employee.getStatus()).thenReturn(ACTIVE);
    when(employee.getRole()).thenReturn(ROLE_DEPARTMENT_HEAD);

    UserRepository userRepository = mock(UserRepository.class);

    department = new Department(departmentId, "Dept", true, createdBy);

    assertThrows(
        NotEmployeeException.class,
        () -> department.updateDepartmentHead(employee, modifier, userRepository));
  }

  @Test
  void whenUpdateDepartmentHead_thenCurrentHeadRevertsAndNewHeadIsAssigned() {
    Employee oldHead = mock(Employee.class);
    when(oldHead.getUsername()).thenReturn("oldHead");
    when(oldHead.getStatus()).thenReturn(ACTIVE);
    when(oldHead.getRole()).thenReturn(ROLE_EMPLOYEE);

    department = new Department(departmentId, "Dept", true, createdBy);
    department.updateDepartmentHead(oldHead, modifier, mock(UserRepository.class));

    Employee newHead = mock(Employee.class);
    when(newHead.getUsername()).thenReturn("newHead");
    when(newHead.getStatus()).thenReturn(ACTIVE);
    when(newHead.getRole()).thenReturn(ROLE_EMPLOYEE);

    UserRepository userRepository = mock(UserRepository.class);
    when(userRepository.findByUsername("oldHead")).thenReturn(Optional.of(oldHead));

    department.updateDepartmentHead(newHead, modifier, userRepository);

    verify(oldHead).changeToEmployee(modifier);
    verify(newHead).changeToDepartmentHead(modifier);
    assertEquals(newHead, department.getDepartmentHead());
    assertEquals(modifier, department.getLastModifiedBy());
  }
}
