package com.antharos.corporateorganization.application.commands.employee.hire;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.employee.*;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeAlreadyExists;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import com.antharos.corporateorganization.domain.employee.valueobject.Name;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleNotFoundException;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class HireEmployeeCommandHandlerUnitTest {

  private UserRepository userRepository;
  private DepartmentRepository departmentRepository;
  private JobTitleRepository jobTitleRepository;
  private EventProducer eventProducer;
  private HireEmployeeCommandHandler commandHandler;

  @BeforeEach
  void setUp() {
    this.userRepository = mock(UserRepository.class);
    this.departmentRepository = mock(DepartmentRepository.class);
    this.jobTitleRepository = mock(JobTitleRepository.class);
    this.eventProducer = mock(EventProducer.class);
    this.commandHandler =
        new HireEmployeeCommandHandler(
            userRepository, departmentRepository, jobTitleRepository, eventProducer);
  }

  @Test
  void whenEmployeeAlreadyExists_thenThrowsEmployeeAlreadyExists() {
    // Build the command using the builder pattern
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(UUID.randomUUID().toString())
            .dni("123456789")
            .name("John")
            .surname("Doe")
            .telephoneNumber("123456789")
            .salary(BigDecimal.valueOf(50000))
            .departmentId(UUID.randomUUID().toString())
            .hiringDate(LocalDate.of(2022, 1, 1))
            .role(Role.ROLE_EMPLOYEE)
            .jobTitleId(UUID.randomUUID().toString())
            .createdBy("admin")
            .build();

    EmployeeId employeeId = EmployeeId.of(command.getUserId());

    when(userRepository.findBy(employeeId)).thenReturn(Optional.of(mock(Employee.class)));

    assertThrows(EmployeeAlreadyExists.class, () -> commandHandler.doHandle(command));

    verify(userRepository, times(1)).findBy(employeeId);
  }

  @Test
  void whenDepartmentNotFound_thenThrowsDepartmentNotFoundException() {
    // Build the command using the builder pattern
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(UUID.randomUUID().toString())
            .dni("123456789")
            .name("John")
            .surname("Doe")
            .telephoneNumber("123456789")
            .salary(BigDecimal.valueOf(50000))
            .departmentId(UUID.randomUUID().toString())
            .hiringDate(LocalDate.of(2022, 1, 1))
            .role(Role.ROLE_EMPLOYEE)
            .jobTitleId(UUID.randomUUID().toString())
            .createdBy("admin")
            .build();

    when(departmentRepository.findBy(DepartmentId.of(command.getDepartmentId())))
        .thenReturn(Optional.empty());

    assertThrows(DepartmentNotFoundException.class, () -> commandHandler.doHandle(command));

    verify(departmentRepository, times(1)).findBy(DepartmentId.of(command.getDepartmentId()));
  }

  @Test
  void whenJobTitleNotFound_thenThrowsJobTitleNotFoundException() {
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(UUID.randomUUID().toString())
            .dni("123456789")
            .name("John")
            .surname("Doe")
            .telephoneNumber("123456789")
            .salary(BigDecimal.valueOf(50000))
            .departmentId(UUID.randomUUID().toString())
            .hiringDate(LocalDate.of(2022, 1, 1))
            .role(Role.ROLE_EMPLOYEE)
            .jobTitleId(UUID.randomUUID().toString())
            .createdBy("admin")
            .build();

    Department mockDepartment = mock(Department.class);

    when(departmentRepository.findBy(any(DepartmentId.class)))
        .thenReturn(Optional.of(mockDepartment));

    when(jobTitleRepository.findBy(JobTitleId.of(command.getJobTitleId())))
        .thenReturn(Optional.empty());

    assertThrows(JobTitleNotFoundException.class, () -> commandHandler.doHandle(command));

    verify(jobTitleRepository, times(1)).findBy(JobTitleId.of(command.getJobTitleId()));
  }

  @Test
  void whenSuccessfullyHiringEmployee_thenEmployeeIsSavedAndEventIsPublished() {
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(UUID.randomUUID().toString())
            .dni("12422154C")
            .name("John")
            .surname("Doe")
            .telephoneNumber("123456789")
            .salary(BigDecimal.valueOf(50000))
            .departmentId(UUID.randomUUID().toString())
            .hiringDate(LocalDate.of(2022, 1, 1))
            .role(Role.ROLE_EMPLOYEE)
            .jobTitleId(UUID.randomUUID().toString())
            .createdBy("admin")
            .build();

    Department mockDepartment = mock(Department.class);
    JobTitle mockJobTitle = mock(JobTitle.class);
    Employee mockEmployee = Employee.builder().name(new Name("random")).build();

    when(userRepository.findBy(any(EmployeeId.class))).thenReturn(Optional.empty());

    when(departmentRepository.findBy(any(DepartmentId.class)))
        .thenReturn(Optional.of(mockDepartment));

    when(jobTitleRepository.findBy(any(JobTitleId.class))).thenReturn(Optional.of(mockJobTitle));

    when(userRepository.findTopByOrderByEmployeeNumberDesc()).thenReturn(Optional.of(mockEmployee));

    commandHandler.doHandle(command);

    ArgumentCaptor<Employee> employeeCaptor = ArgumentCaptor.forClass(Employee.class);
    verify(userRepository, times(1)).save(employeeCaptor.capture());

    assertNotNull(employeeCaptor.getValue());
    verify(eventProducer, times(1)).sendEmployeeHiredEvent(any());
  }

  @Test
  void whenEmployeeNumberIsCalculated_thenCorrectEmployeeNumberIsAssigned() {
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(UUID.randomUUID().toString())
            .dni("12422154C")
            .name("John")
            .surname("Doe")
            .telephoneNumber("123456789")
            .salary(BigDecimal.valueOf(50000))
            .departmentId(UUID.randomUUID().toString())
            .hiringDate(LocalDate.of(2022, 1, 1))
            .role(Role.ROLE_EMPLOYEE)
            .jobTitleId(UUID.randomUUID().toString())
            .createdBy("admin")
            .build();

    when(userRepository.findBy(any(EmployeeId.class))).thenReturn(Optional.empty());
    when(departmentRepository.findBy(any(DepartmentId.class)))
        .thenReturn(Optional.of(mock(Department.class)));
    when(jobTitleRepository.findBy(any(JobTitleId.class)))
        .thenReturn(Optional.of(mock(JobTitle.class)));
    when(userRepository.findTopByOrderByEmployeeNumberDesc())
        .thenReturn(Optional.of(mock(Employee.class)));

    commandHandler.doHandle(command);

    verify(userRepository, times(1)).findTopByOrderByEmployeeNumberDesc();
  }
}
