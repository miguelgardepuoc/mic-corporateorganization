package com.antharos.corporateorganization.domain.employee.valueobject;

import java.util.Objects;

public record TelephoneNumber(String value) {
  public TelephoneNumber {
    Objects.requireNonNull(value, "Telephone number cannot be null");
    if (!value.matches("\\+?\\d{9,15}")) {
      throw new IllegalArgumentException("Invalid telephone number format");
    }
  }
}
