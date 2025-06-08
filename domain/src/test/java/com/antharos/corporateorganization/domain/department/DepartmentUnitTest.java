package com.antharos.corporateorganization.domain.department;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_DEPARTMENT_HEAD;
import static com.antharos.corporateorganization.domain.employee.Role.ROLE_EMPLOYEE;
import static com.antharos.corporateorganization.domain.employee.Status.ACTIVE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.exception.DepartmentHasActiveEmployeesException;
import com.antharos.corporateorganization.domain.department.exception.NotActiveUserException;
import com.antharos.corporateorganization.domain.department.exception.NotEmployeeException;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import com.antharos.corporateorganization.domain.globalexceptions.ConflictException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DepartmentUnitTest {

  private EmployeeRepository employeeRepository;
  private DepartmentId departmentId;
  private Department department;
  private final String createdBy = "admin";
  private final String modifier = "modifier";

  @BeforeEach
  void setUp() {
    departmentId = DepartmentId.of(UUID.randomUUID().toString());
    department = Department.create(departmentId, "Initial Desc", createdBy);
    employeeRepository = mock(EmployeeRepository.class);
  }

  @Test
  void whenRenameOnActiveDepartment_thenDescriptionIsUpdated() {
    department.rename("New Description", modifier);
    assertEquals("New Description", department.getDescription());
    assertEquals(modifier, department.getLastModifiedBy());
  }

  @Test
  void whenRenameOnInactiveDepartment_thenThrowConflictException() {
    department.remove(modifier, this.employeeRepository);
    assertThrows(ConflictException.class, () -> department.rename("Name", modifier));
  }

  @Test
  void whenRemoveOnInactiveDepartment_thenThrowConflictException() {
    department.remove(modifier, this.employeeRepository);
    assertThrows(
        ConflictException.class, () -> department.remove(modifier, this.employeeRepository));
  }

  @Test
  void whenRemoveOnActiveDepartment_thenIsActiveBecomesFalse() {
    when(employeeRepository.hasActiveEmployeesByDepartmentId(departmentId)).thenReturn(false);
    department.remove(modifier, this.employeeRepository);
    assertFalse(department.isActive());
    assertEquals(modifier, department.getLastModifiedBy());
  }

  @Test
  void whenRemoveDepartmentWithActiveEmployees_thenThrowException() {
    when(employeeRepository.hasActiveEmployeesByDepartmentId(departmentId)).thenReturn(true);
    assertThrows(
        DepartmentHasActiveEmployeesException.class,
        () -> department.remove(modifier, this.employeeRepository));
  }

  @Test
  void whenUpdateDepartmentHeadWithInactiveDepartment_thenThrowConflictException() {
    department.remove(modifier, this.employeeRepository);

    Employee employee = mock(Employee.class);

    assertThrows(
        ConflictException.class, () -> department.updateDepartmentHead(employee, modifier));
  }

  @Test
  void whenUpdateDepartmentHeadWithInactiveEmployee_thenThrowNotActiveUserException() {
    Employee employee = mock(Employee.class);

    assertThrows(
        NotActiveUserException.class, () -> department.updateDepartmentHead(employee, modifier));
  }

  @Test
  void whenNotEmployeeRole_thenThrowNotEmployeeException() {
    Employee employee = mock(Employee.class);
    when(employee.getUsername()).thenReturn("oldHead");
    when(employee.getStatus()).thenReturn(ACTIVE);
    when(employee.getRole()).thenReturn(ROLE_DEPARTMENT_HEAD);

    department = new Department(departmentId, "Dept", true, createdBy);

    assertThrows(
        NotEmployeeException.class, () -> department.updateDepartmentHead(employee, modifier));
  }

  @Test
  void whenUpdateDepartmentHead_thenCurrentHeadRevertsAndNewHeadIsAssigned() {
    Employee oldHead = mock(Employee.class);
    when(oldHead.getUsername()).thenReturn("oldHead");
    when(oldHead.getStatus()).thenReturn(ACTIVE);
    when(oldHead.getRole()).thenReturn(ROLE_EMPLOYEE);

    department = new Department(departmentId, "Dept", true, createdBy);
    when(oldHead.getDepartment()).thenReturn(mock(Department.class));
    department.updateDepartmentHead(oldHead, modifier);

    Employee newHead = mock(Employee.class);
    when(newHead.getUsername()).thenReturn("newHead");
    when(newHead.getStatus()).thenReturn(ACTIVE);
    when(newHead.getRole()).thenReturn(ROLE_EMPLOYEE);

    EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    when(employeeRepository.findByUsername("oldHead")).thenReturn(Optional.of(oldHead));
    when(newHead.getDepartment()).thenReturn(mock(Department.class));
    department.updateDepartmentHead(newHead, modifier);

    verify(newHead).changeToDepartmentHead(modifier);
    assertEquals(newHead, department.getDepartmentHead());
    assertEquals(modifier, department.getLastModifiedBy());
  }
}
