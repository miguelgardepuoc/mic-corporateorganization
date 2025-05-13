package com.antharos.corporateorganization.application.commands.department.create;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentAlreadyExistsException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CreateDepartmentCommandHandlerUnitTest {

  private DepartmentRepository departmentRepository;
  private CreateDepartmentCommandHandler handler;

  @BeforeEach
  void setUp() {
    departmentRepository = mock(DepartmentRepository.class);
    handler = new CreateDepartmentCommandHandler(departmentRepository);
  }

  @Test
  void whenDepartmentAlreadyExists_thenThrowsException() {
    String id = UUID.randomUUID().toString();
    CreateDepartmentCommand command = new CreateDepartmentCommand(id, "Finance", "admin");
    DepartmentId departmentId = DepartmentId.of(id);
    Department existingDepartment = mock(Department.class);

    when(departmentRepository.findBy(departmentId)).thenReturn(Optional.of(existingDepartment));

    assertThrows(DepartmentAlreadyExistsException.class, () -> handler.doHandle(command));

    verify(departmentRepository, times(1)).findBy(departmentId);
    verify(departmentRepository, never()).save(any());
  }

  @Test
  void whenDepartmentDoesNotExist_thenCreatesAndSavesDepartment() {
    String id = UUID.randomUUID().toString();
    String description = "Research";
    String createdBy = "admin";
    CreateDepartmentCommand command = new CreateDepartmentCommand(id, description, createdBy);
    DepartmentId departmentId = DepartmentId.of(id);

    when(departmentRepository.findBy(departmentId)).thenReturn(Optional.empty());

    handler.doHandle(command);

    verify(departmentRepository, times(1)).findBy(departmentId);
    verify(departmentRepository, times(1)).save(any(Department.class));
  }
}
