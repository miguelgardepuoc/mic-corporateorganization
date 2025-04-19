package com.antharos.corporateorganization.infrastructure.in.dto.employee;

import com.antharos.corporateorganization.domain.user.*;
import java.math.BigDecimal;
import java.util.Date;

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
    Date hiringDate,
    String jobTitleId,
    Status status) {}
