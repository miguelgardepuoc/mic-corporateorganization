package com.antharos.corporateorganization.domain.user;

import java.time.LocalDate;
import java.util.Objects;

public record HiringDate(LocalDate value) {
  public HiringDate {
    Objects.requireNonNull(value, "Hiring date cannot be null");
    if (value.isBefore(LocalDate.now())) {
      throw new IllegalArgumentException("Hiring date cannot be in the past");
    }
  }
}
