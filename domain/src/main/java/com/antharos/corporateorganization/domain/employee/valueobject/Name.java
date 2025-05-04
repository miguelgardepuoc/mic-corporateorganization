package com.antharos.corporateorganization.domain.employee.valueobject;

import com.antharos.corporateorganization.domain.employee.exception.InvalidNameException;
import java.util.Objects;

public record Name(String value) {
  public Name {
    Objects.requireNonNull(value, "Name cannot be null");
    if (value.isBlank()) {
      throw new InvalidNameException("Name cannot be blank");
    }
    if (value.matches("\\d+")) {
      throw new InvalidNameException("Name cannot be a number");
    }
    if (!value.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'\\-\\s]+")) {
      throw new InvalidNameException("Name contains invalid characters");
    }
  }
}
