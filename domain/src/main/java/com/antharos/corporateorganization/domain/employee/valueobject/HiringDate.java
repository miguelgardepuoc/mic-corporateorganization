package com.antharos.corporateorganization.domain.employee.valueobject;

import java.time.LocalDate;
import java.util.Objects;

public record HiringDate(LocalDate value) {
  public HiringDate {
    Objects.requireNonNull(value, "Hiring date cannot be null");
  }
}
