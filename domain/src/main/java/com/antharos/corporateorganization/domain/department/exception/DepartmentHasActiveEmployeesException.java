package com.antharos.corporateorganization.domain.department.exception;

public class DepartmentHasActiveEmployeesException extends IllegalArgumentException {
  public DepartmentHasActiveEmployeesException(String departmentId) {
    super("Department : " + departmentId + " has active employees.");
  }
}
