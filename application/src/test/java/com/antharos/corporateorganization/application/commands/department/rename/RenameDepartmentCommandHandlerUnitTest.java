package com.antharos.corporateorganization.application.commands.department.rename;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RenameDepartmentCommandHandlerUnitTest {

  private DepartmentRepository departmentRepository;
  private RenameDepartmentCommandHandler handler;

  @BeforeEach
  void setUp() {
    departmentRepository = mock(DepartmentRepository.class);
    handler = new RenameDepartmentCommandHandler(departmentRepository);
  }

  @Test
  void whenDepartmentExists_thenRenameAndSave() {
    String departmentIdStr = UUID.randomUUID().toString();
    String user = "admin";
    String newDescription = "New Department Name";

    RenameDepartmentCommand command =
        new RenameDepartmentCommand(departmentIdStr, newDescription, user);
    DepartmentId departmentId = DepartmentId.of(departmentIdStr);
    Department mockDepartment = mock(Department.class);

    when(departmentRepository.findBy(departmentId)).thenReturn(Optional.of(mockDepartment));

    handler.handle(command);

    verify(departmentRepository).findBy(departmentId);
    verify(mockDepartment).rename(newDescription, user);
    verify(departmentRepository).save(mockDepartment);
  }

  @Test
  void whenDepartmentNotFound_thenThrowsException() {
    String departmentIdStr = UUID.randomUUID().toString();
    RenameDepartmentCommand command =
        new RenameDepartmentCommand(departmentIdStr, "Updated Description", "admin");
    DepartmentId departmentId = DepartmentId.of(departmentIdStr);

    when(departmentRepository.findBy(departmentId)).thenReturn(Optional.empty());

    assertThrows(DepartmentNotFoundException.class, () -> handler.handle(command));

    verify(departmentRepository).findBy(departmentId);
    verify(departmentRepository, never()).save(any());
  }
}
