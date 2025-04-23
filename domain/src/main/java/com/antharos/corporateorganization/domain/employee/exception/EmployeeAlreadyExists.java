package com.antharos.corporateorganization.domain.employee.exception;

import com.antharos.corporateorganization.domain.globalexceptions.AlreadyExistsException;

public class EmployeeAlreadyExists extends AlreadyExistsException {
  public EmployeeAlreadyExists() {
    super("User already exists");
  }
}
