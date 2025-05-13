package com.antharos.corporateorganization.domain.employee;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_EMPLOYEE;
import static com.antharos.corporateorganization.domain.employee.Status.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.*;
import com.antharos.corporateorganization.domain.globalexceptions.ConflictException;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeUnitTest {

  private UserRepository userRepository;
  private EventProducer eventProducer;

  @BeforeEach
  void setUp() {
    userRepository = mock(UserRepository.class);
    eventProducer = mock(EventProducer.class);
  }

  @Test
  void whenHireNewEmployee_thenUsernameAndEmailAreGenerated() {
    when(userRepository.usernameExists("jdoe")).thenReturn(false);

    Employee employee =
        Employee.hire(
            EmployeeId.of(UUID.randomUUID().toString()),
            123L,
            Dni.of("12345678A"),
            new Name("John"),
            new Surname("Doe"),
            new TelephoneNumber("600123456"),
            new Salary(new BigDecimal("30000.0")),
            mock(Department.class),
            new HiringDate(LocalDate.now()),
            ROLE_EMPLOYEE,
            mock(JobTitle.class),
            "admin",
            userRepository);

    assertNotNull(employee.getUsername());
    assertEquals(employee.getUsername() + "@antharos.com", employee.getCorporateEmail());
    assertEquals(ACTIVE, employee.getStatus());
  }

  @Test
  void whenHireNewEmployeeWithoutRole_thenUsernameAndEmailAreGenerated() {
    when(userRepository.usernameExists("jdoe")).thenReturn(false);

    Employee employee =
        Employee.hire(
            EmployeeId.of(UUID.randomUUID().toString()),
            123L,
            Dni.of("12345678A"),
            new Name("John"),
            new Surname("Doe"),
            new TelephoneNumber("600123456"),
            new Salary(new BigDecimal("30000.0")),
            mock(Department.class),
            new HiringDate(LocalDate.now()),
            null,
            mock(JobTitle.class),
            "admin",
            userRepository);

    assertNotNull(employee.getUsername());
    assertEquals(employee.getUsername() + "@antharos.com", employee.getCorporateEmail());
    assertEquals(ACTIVE, employee.getStatus());
  }

  @Test
  void whenHireWithConflictingUsername_thenRandomSuffixIsAdded() {
    when(userRepository.usernameExists("jdoe")).thenReturn(true);

    Employee employee =
        Employee.hire(
            EmployeeId.of(UUID.randomUUID().toString()),
            123L,
            Dni.of("12345678A"),
            new Name("John"),
            new Surname("Doe"),
            new TelephoneNumber("600123456"),
            new Salary(new BigDecimal("30000.0")),
            mock(Department.class),
            new HiringDate(LocalDate.now()),
            ROLE_EMPLOYEE,
            mock(JobTitle.class),
            "admin",
            userRepository);

    assertTrue(employee.getUsername().startsWith("jdoe"));
    assertTrue(employee.getUsername().length() > "jdoe".length());
  }

  @Test
  void givenUser_whenSignup_thenPasswordAndModifierAreUpdated() {
    Employee employee = new Employee(EmployeeId.of(UUID.randomUUID().toString()));
    String expectedPassword = "securePassword123";
    String expectedModifier = "adminUser";

    employee.signup(expectedPassword, expectedModifier);

    assertEquals(expectedPassword, employee.getPassword());
    assertEquals(expectedModifier, employee.getLastModifiedBy());
  }

  @Test
  void whenPutOnLeaveWhileAlreadyOnLeave_thenThrowConflictException() {
    Employee employee = createDefaultEmployeeWithStatus(ON_LEAVE);

    assertThrows(ConflictException.class, () -> employee.putOnLeave("admin"));
  }

  @Test
  void whenPutOnLeave_thenStatusChangesToOnLeave() {
    Employee employee = createDefaultEmployeeWithStatus(ACTIVE);

    employee.putOnLeave("admin");

    assertEquals(ON_LEAVE, employee.getStatus());
    assertEquals("admin", employee.getLastModifiedBy());
  }

  @Test
  void whenTerminateWhileAlreadyTerminated_thenThrowConflictException() {
    Employee employee = createDefaultEmployeeWithStatus(TERMINATED);

    assertThrows(ConflictException.class, () -> employee.terminate("admin"));
  }

  @Test
  void whenTerminate_thenStatusChangesToTerminated() {
    Employee employee = createDefaultEmployeeWithStatus(ACTIVE);

    employee.terminate("admin");

    assertEquals(TERMINATED, employee.getStatus());
    assertEquals("admin", employee.getLastModifiedBy());
  }

  @Test
  void whenMarkAsInactiveWhileAlreadyInactive_thenThrowConflictException() {
    Employee employee = createDefaultEmployeeWithStatus(INACTIVE);

    assertThrows(ConflictException.class, () -> employee.markAsInactive("admin", eventProducer));
  }

  @Test
  void whenMarkAsInactive_thenStatusChangesToInactiveAndEventIsSent() {
    Employee employee = createDefaultEmployeeWithStatus(ACTIVE);

    employee.markAsInactive("admin", eventProducer);

    assertEquals(INACTIVE, employee.getStatus());
    verify(eventProducer).sendEmployeeMarkedAsInactiveEvent(employee);
    assertEquals("admin", employee.getLastModifiedBy());
  }

  @Test
  void whenChangeToDepartmentHead_thenRoleChanges() {
    Employee employee = createDefaultEmployeeWithStatus(ACTIVE);

    employee.changeToDepartmentHead("admin");

    assertEquals(Role.ROLE_DEPARTMENT_HEAD, employee.getRole());
    assertEquals("admin", employee.getLastModifiedBy());
  }

  @Test
  void whenChangeToEmployee_thenRoleChanges() {
    Employee employee = createDefaultEmployeeWithStatus(ACTIVE);
    employee.changeToDepartmentHead("admin");

    employee.changeToEmployee("hr");

    assertEquals(ROLE_EMPLOYEE, employee.getRole());
    assertEquals("hr", employee.getLastModifiedBy());
  }

  private Employee createDefaultEmployeeWithStatus(Status status) {
    return Employee.builder()
        .id(EmployeeId.of(UUID.randomUUID().toString()))
        .employeeNumber(123L)
        .username("jdoe")
        .dni(Dni.of("12345678A"))
        .name(new Name("John"))
        .surname(new Surname("Doe"))
        .telephoneNumber(new TelephoneNumber("600123456"))
        .department(mock(Department.class))
        .salary(new Salary(new BigDecimal("30000.0")))
        .hiringDate(new HiringDate(LocalDate.now()))
        .role(ROLE_EMPLOYEE)
        .jobTitle(mock(JobTitle.class))
        .password("secret")
        .corporateEmail("jdoe@antharos.com")
        .status(status)
        .createdBy("admin")
        .createdAt(LocalDate.now())
        .lastModifiedBy("admin")
        .lastModifiedAt(LocalDate.now())
        .build();
  }
}
