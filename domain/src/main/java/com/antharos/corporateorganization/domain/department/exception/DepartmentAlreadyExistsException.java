package com.antharos.corporateorganization.domain.department.exception;

import com.antharos.corporateorganization.domain.globalexceptions.AlreadyExistsException;

public class DepartmentAlreadyExistsException extends AlreadyExistsException {
  public DepartmentAlreadyExistsException() {
    super("Department already exists");
  }
}
