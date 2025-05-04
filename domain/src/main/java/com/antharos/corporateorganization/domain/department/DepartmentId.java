package com.antharos.corporateorganization.domain.department;

import com.antharos.corporateorganization.domain.employee.exception.InvalidDepartmentException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentId {

  String valueAsString;

  public static DepartmentId of(String departmentId) {
    try {
      UUID.fromString(departmentId);
    } catch (IllegalArgumentException e) {
      throw new InvalidDepartmentException(
          "DepartmentId must be a valid UUID. Invalid string value: " + departmentId);
    }
    return new DepartmentId(departmentId);
  }
}
