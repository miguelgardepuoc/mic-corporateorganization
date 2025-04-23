package com.antharos.corporateorganization.infrastructure.in.dto.employee;

import com.antharos.corporateorganization.domain.employee.Role;
import java.math.BigDecimal;
import java.time.LocalDate;

public record HireEmployeeRequest(
    String id,
    String dni,
    String name,
    String surname,
    String telephoneNumber,
    BigDecimal salary,
    String departmentId,
    LocalDate hiringDate,
    Role role,
    String jobTitleId) {}
