package com.antharos.corporateorganization.domain.employee.valueobject;

import com.antharos.corporateorganization.domain.employee.exception.InvalidSurnameException;
import java.util.Objects;

public record Surname(String value) {
  public Surname {
    Objects.requireNonNull(value, "Name cannot be null");
    if (value.isBlank()) {
      throw new InvalidSurnameException("Name cannot be blank");
    }
    if (value.matches("\\d+")) {
      throw new InvalidSurnameException("Name cannot be a number");
    }
    if (!value.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ'\\-\\s]+")) {
      throw new InvalidSurnameException("Name contains invalid characters");
    }
  }
}
