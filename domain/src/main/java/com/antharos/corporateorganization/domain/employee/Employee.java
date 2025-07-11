package com.antharos.corporateorganization.domain.employee;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_DEPARTMENT_HEAD;
import static com.antharos.corporateorganization.domain.employee.Role.ROLE_EMPLOYEE;

import com.antharos.corporateorganization.domain.dddbase.AggregateRoot;
import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.employee.event.EmployeeHiredEvent;
import com.antharos.corporateorganization.domain.employee.event.EmployeeMarkedAsInactiveEvent;
import com.antharos.corporateorganization.domain.employee.event.EmployeeOnLeaveEvent;
import com.antharos.corporateorganization.domain.employee.event.EmployeeTerminatedEvent;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.valueobject.*;
import com.antharos.corporateorganization.domain.globalexceptions.ConflictException;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import java.time.LocalDate;
import java.util.Random;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Employee extends AggregateRoot {
  private final EmployeeId id;

  private Dni dni;

  private Name name;

  private Surname surname;

  private Role role;

  private TelephoneNumber telephoneNumber;

  private String username;

  private String password;

  private Long employeeNumber;

  private Department department;

  private String corporateEmail;

  private Salary salary;

  private HiringDate hiringDate;

  private Status status;

  private JobTitle jobTitle;

  private String createdBy;

  private LocalDate createdAt;

  private String lastModifiedBy;

  private LocalDate lastModifiedAt;

  @Builder
  public Employee(
      EmployeeId id,
      Long employeeNumber,
      Dni dni,
      String username,
      String password,
      Name name,
      Surname surname,
      TelephoneNumber telephoneNumber,
      String corporateEmail,
      Status status,
      Department department,
      Salary salary,
      HiringDate hiringDate,
      Role role,
      JobTitle jobTitle,
      String createdBy,
      LocalDate createdAt,
      String lastModifiedBy,
      LocalDate lastModifiedAt) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.employeeNumber = employeeNumber;
    this.dni = dni;
    this.name = name;
    this.surname = surname;
    this.telephoneNumber = telephoneNumber;
    this.corporateEmail = corporateEmail;
    this.department = department;
    this.salary = salary;
    this.hiringDate = hiringDate;
    this.role = role;
    this.status = status;
    this.jobTitle = jobTitle;
    this.createdBy = createdBy;
    this.createdAt = createdAt;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedAt = lastModifiedAt;
  }

  public Employee(EmployeeId id) {
    this.id = id;
  }

  public static Employee hire(
      EmployeeId employeeId,
      Long employeeNumber,
      Dni dni,
      Name name,
      Surname surname,
      TelephoneNumber telephoneNumber,
      Salary salary,
      Department department,
      HiringDate hiringDate,
      Role role,
      JobTitle jobTitle,
      String createdBy,
      EmployeeRepository employeeRepository) {

    Employee u =
        new Employee(
            employeeId,
            employeeNumber,
            dni,
            null,
            null,
            name,
            surname,
            telephoneNumber,
            null,
            Status.ACTIVE,
            department,
            salary,
            hiringDate,
            role != null ? role : ROLE_EMPLOYEE,
            jobTitle,
            createdBy,
            null,
            null,
            null);

    u.username = u.generateUsername(employeeRepository);
    u.corporateEmail = u.username + "@antharos.com";
    u.status = Status.ACTIVE;
    u.addDomainEvent(new EmployeeHiredEvent(u));

    return u;
  }

  private String generateUsername(EmployeeRepository employeeRepository) {
    String nameInitial = this.name.value().trim().replaceAll("\\s+", "").substring(0, 1);
    String cleanSurname = this.surname.value().trim().replaceAll("\\s+", "");
    String baseUsername = (nameInitial + cleanSurname).toLowerCase();

    if (employeeRepository.usernameExists(baseUsername)) {
      Random random = new Random();
      baseUsername = baseUsername + random.nextInt(10000);
    }

    return baseUsername;
  }

  public void signup(final String password, final String modificationUser) {
    this.password = password;
    this.lastModifiedBy = modificationUser;
  }

  public void putOnLeave(final String modificationUser) {
    if (Status.ON_LEAVE.equals(this.status)) {
      throw new ConflictException();
    }
    this.status = Status.ON_LEAVE;
    this.lastModifiedBy = modificationUser;
    this.addDomainEvent(new EmployeeOnLeaveEvent(this));
  }

  public void terminate(final String modificationUser) {
    if (Status.TERMINATED.equals(this.status)) {
      throw new ConflictException();
    }
    this.status = Status.TERMINATED;
    this.lastModifiedBy = modificationUser;
    this.addDomainEvent(new EmployeeTerminatedEvent(this));
  }

  public void markAsInactive(final String modificationUser, EventProducer eventProducer) {
    if (Status.INACTIVE.equals(this.status)) {
      throw new ConflictException();
    }
    this.status = Status.INACTIVE;
    this.lastModifiedBy = modificationUser;
    eventProducer.sendEmployeeMarkedAsInactiveEvent(this);
    this.addDomainEvent(new EmployeeMarkedAsInactiveEvent(this));
  }

  public void changeToDepartmentHead(final String modificationUser) {
    this.role = ROLE_DEPARTMENT_HEAD;
    this.lastModifiedBy = modificationUser;
  }

  public void changeToEmployee(final String modificationUser) {
    this.role = ROLE_EMPLOYEE;
    this.lastModifiedBy = modificationUser;
  }
}
