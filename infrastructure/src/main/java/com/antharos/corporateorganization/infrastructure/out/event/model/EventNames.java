package com.antharos.corporateorganization.infrastructure.out.event.model;

import lombok.Getter;

@Getter
public enum EventNames {
  EMPLOYEE_HIRED("EmployeeHired"),
  EMPLOYEE_ON_LEAVE("EmployeeOnLeave"),
  EMPLOYEE_TERMINATED("EmployeeTerminated"),
  EMPLOYEE_MARKED_AS_INACTIVE("EmployeeMarkedAsInactive");

  private final String description;

  EventNames(String description) {
    this.description = description;
  }
}
