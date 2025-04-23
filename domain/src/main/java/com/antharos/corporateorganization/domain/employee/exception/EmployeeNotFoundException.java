package com.antharos.corporateorganization.domain.employee.exception;

import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;

public class EmployeeNotFoundException extends NotFoundException {
  public EmployeeNotFoundException(String userId) {
    super("User not found with ID: " + userId);
  }
}
