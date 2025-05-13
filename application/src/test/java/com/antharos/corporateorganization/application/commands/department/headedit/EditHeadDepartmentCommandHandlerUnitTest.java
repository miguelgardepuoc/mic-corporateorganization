package com.antharos.corporateorganization.application.commands.department.headedit;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.*;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.UsernameNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EditHeadDepartmentCommandHandlerUnitTest {

  private DepartmentRepository departmentRepository;
  private UserRepository userRepository;
  private EditHeadDepartmentCommandHandler handler;

  @BeforeEach
  void setUp() {
    departmentRepository = mock(DepartmentRepository.class);
    userRepository = mock(UserRepository.class);
    handler = new EditHeadDepartmentCommandHandler(departmentRepository, userRepository);
  }

  @Test
  void whenDepartmentNotFound_thenThrowsException() {
    String deptId = UUID.randomUUID().toString();
    String username = "john.doe";
    EditHeadDepartmentCommand command = new EditHeadDepartmentCommand(deptId, username, "admin");

    when(departmentRepository.findBy(DepartmentId.of(deptId))).thenReturn(Optional.empty());

    assertThrows(DepartmentNotFoundException.class, () -> handler.handle(command));

    verify(departmentRepository, times(1)).findBy(DepartmentId.of(deptId));
    verifyNoInteractions(userRepository);
  }

  @Test
  void whenUsernameNotFound_thenThrowsException() {
    String deptId = UUID.randomUUID().toString();
    String username = "john.doe";
    EditHeadDepartmentCommand command = new EditHeadDepartmentCommand(deptId, username, "admin");

    Department mockDepartment = mock(Department.class);
    when(departmentRepository.findBy(DepartmentId.of(deptId)))
        .thenReturn(Optional.of(mockDepartment));
    when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class, () -> handler.handle(command));

    verify(departmentRepository, times(1)).findBy(DepartmentId.of(deptId));
    verify(userRepository, times(1)).findByUsername(username);
    verifyNoMoreInteractions(departmentRepository);
  }

  @Test
  void whenValidCommand_thenUpdatesDepartmentHeadAndSaves() {
    String deptId = UUID.randomUUID().toString();
    String username = "john.doe";
    String admin = "admin";

    EditHeadDepartmentCommand command = new EditHeadDepartmentCommand(deptId, username, admin);

    Department mockDepartment = mock(Department.class);
    Employee mockEmployee = mock(Employee.class);

    when(departmentRepository.findBy(DepartmentId.of(deptId)))
        .thenReturn(Optional.of(mockDepartment));
    when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockEmployee));

    handler.handle(command);

    verify(mockDepartment, times(1)).updateDepartmentHead(mockEmployee, admin, userRepository);
    verify(departmentRepository, times(1)).save(mockDepartment);
    verify(userRepository, times(1)).save(mockEmployee);
  }
}
