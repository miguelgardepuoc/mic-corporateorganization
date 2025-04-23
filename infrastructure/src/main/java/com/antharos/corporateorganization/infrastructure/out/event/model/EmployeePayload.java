package com.antharos.corporateorganization.infrastructure.out.event.model;

import com.antharos.corporateorganization.domain.employee.Role;
import com.antharos.corporateorganization.domain.employee.Status;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeePayload {
  private UUID id;
  private String dni;
  private String name;
  private String surname;
  private Role role;
  private String telephoneNumber;
  private String username;
  private Long employeeNumber;
  private String departmentId;
  private String corporateEmail;
  private BigDecimal salary;
  private LocalDate hiringDate;
  private Status status;
  private String jobTitleId;
}
