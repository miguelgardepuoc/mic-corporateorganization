package com.antharos.corporateorganization.application.commands.department.headedit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.DepartmentManagementService;
import com.antharos.corporateorganization.domain.department.*;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditHeadDepartmentCommandHandlerUnitTest {

  private DepartmentManagementService departmentManagementService;
  private EditHeadDepartmentCommandHandler handler;

  @BeforeEach
  void setUp() {
    departmentManagementService = mock(DepartmentManagementService.class);
    handler = new EditHeadDepartmentCommandHandler(departmentManagementService);
  }

  @Test
  void whenValidCommand_thenAssignsDepartmentHead() {
    String departmentId = UUID.randomUUID().toString();
    EditHeadDepartmentCommand command =
        new EditHeadDepartmentCommand(departmentId, "john.doe", "admin");

    handler.handle(command);

    verify(departmentManagementService)
        .assignDepartmentHead(DepartmentId.of(departmentId), "john.doe", "admin");
  }

  @Test
  void whenDepartmentNotFound_thenThrowsException() {
    EditHeadDepartmentCommand command =
        new EditHeadDepartmentCommand(UUID.randomUUID().toString(), "john.doe", "admin");
    doThrow(new DepartmentNotFoundException("Department not found"))
        .when(departmentManagementService)
        .assignDepartmentHead(any(DepartmentId.class), eq("john.doe"), eq("admin"));

    assertThrows(DepartmentNotFoundException.class, () -> handler.handle(command));
  }
}
