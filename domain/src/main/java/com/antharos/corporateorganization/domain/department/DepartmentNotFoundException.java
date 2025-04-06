package com.antharos.corporateorganization.domain.department;

import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;

public class DepartmentNotFoundException extends NotFoundException {
  public DepartmentNotFoundException(String departmentId) {
    super("Department not found with ID: " + departmentId);
  }
}
