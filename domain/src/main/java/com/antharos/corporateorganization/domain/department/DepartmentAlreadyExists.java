package com.antharos.corporateorganization.domain.department;

import com.antharos.corporateorganization.domain.globalexceptions.AlreadyExistsException;

public class DepartmentAlreadyExists extends AlreadyExistsException {
  public DepartmentAlreadyExists() {
    super("Department already exists");
  }
}
