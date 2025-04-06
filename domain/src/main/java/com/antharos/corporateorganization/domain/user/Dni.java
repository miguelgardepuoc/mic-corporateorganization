package com.antharos.corporateorganization.domain.user;

import java.util.Objects;

public record Dni(String value) {
  public Dni {
    Objects.requireNonNull(value, "DNI cannot be null");
    if (!value.matches("\\d{8}[A-Za-z]")) {
      throw new IllegalArgumentException("Invalid DNI format");
    }
  }
}
