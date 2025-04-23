package com.antharos.corporateorganization.domain.employee.valueobject;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class EmployeeId {

  String valueAsString;

  public static EmployeeId of(String userId) {
    try {
      UUID.fromString(userId);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "UserId must be a valid UUID. Invalid string value: " + userId);
    }
    return new EmployeeId(userId);
  }
}
