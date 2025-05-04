package com.antharos.corporateorganization.domain.employee.valueobject;

import com.antharos.corporateorganization.domain.employee.exception.InvalidTelephoneException;
import java.util.Objects;

public record TelephoneNumber(String value) {
  public TelephoneNumber {
    Objects.requireNonNull(value, "Telephone number cannot be null");
    if (!value.matches("\\+?\\d{9,15}")) {
      throw new InvalidTelephoneException("Invalid telephone number format");
    }
  }
}
