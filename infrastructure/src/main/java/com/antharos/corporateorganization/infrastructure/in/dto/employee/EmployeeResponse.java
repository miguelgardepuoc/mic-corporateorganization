package com.antharos.corporateorganization.infrastructure.in.dto.employee;

import com.antharos.corporateorganization.domain.employee.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmployeeResponse(
    String id,
    Long employeeNumber,
    String dni,
    String name,
    String surname,
    Role role,
    String telephoneNumber,
    String username,
    String departmentId,
    String corporateEmail,
    BigDecimal salary,
    LocalDate hiringDate,
    String jobTitleId,
    Status status) {}
