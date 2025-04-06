package com.antharos.corporateorganization.domain.user;

import java.math.BigDecimal;
import java.util.Objects;

public record Salary(BigDecimal amount) {
  public Salary {
    Objects.requireNonNull(amount, "Salary cannot be null");
    if (amount.compareTo(BigDecimal.ZERO) < 0) {
      throw new IllegalArgumentException("Salary cannot be negative");
    }
  }
}
