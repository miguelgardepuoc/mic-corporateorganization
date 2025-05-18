package com.antharos.corporateorganization.application.commands.department.disable;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DisableDepartmentCommandHandlerUnitTest {

  private DepartmentRepository departmentRepository;
  private EmployeeRepository employeeRepository;
  private DisableDepartmentCommandHandler handler;

  @BeforeEach
  void setUp() {
    departmentRepository = mock(DepartmentRepository.class);
    employeeRepository = mock(EmployeeRepository.class);
    handler = new DisableDepartmentCommandHandler(departmentRepository, employeeRepository);
  }

  @Test
  void whenDepartmentExists_thenRemoveAndSave() {
    String departmentIdStr = UUID.randomUUID().toString();
    String user = "admin";
    DisableDepartmentCommand command = new DisableDepartmentCommand(departmentIdStr, user);
    DepartmentId departmentId = DepartmentId.of(departmentIdStr);
    Department mockDepartment = mock(Department.class);

    when(departmentRepository.findBy(departmentId)).thenReturn(Optional.of(mockDepartment));

    handler.handle(command);

    verify(departmentRepository, times(1)).findBy(departmentId);
    verify(mockDepartment, times(1)).remove(user, this.employeeRepository);
    verify(departmentRepository, times(1)).save(mockDepartment);
  }

  @Test
  void whenDepartmentNotFound_thenThrowsException() {
    String departmentIdStr = UUID.randomUUID().toString();
    DisableDepartmentCommand command = new DisableDepartmentCommand(departmentIdStr, "admin");
    DepartmentId departmentId = DepartmentId.of(departmentIdStr);

    when(departmentRepository.findBy(departmentId)).thenReturn(Optional.empty());

    assertThrows(DepartmentNotFoundException.class, () -> handler.handle(command));

    verify(departmentRepository, times(1)).findBy(departmentId);
    verify(departmentRepository, never()).save(any());
  }
}
