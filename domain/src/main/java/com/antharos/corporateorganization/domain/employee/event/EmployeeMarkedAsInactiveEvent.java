package com.antharos.corporateorganization.domain.employee.event;

import com.antharos.corporateorganization.domain.employee.Employee;

public record EmployeeMarkedAsInactiveEvent(Employee employee) {}
