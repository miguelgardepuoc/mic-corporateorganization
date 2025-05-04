package com.antharos.corporateorganization.domain.employee.valueobject;

import com.antharos.corporateorganization.domain.employee.exception.InvalidSalaryException;
import java.math.BigDecimal;
import java.util.Objects;

public record Salary(BigDecimal amount) {
  private static final BigDecimal MIN_ANNUAL_SALARY = new BigDecimal("20000.00");
  private static final BigDecimal MAX_ANNUAL_SALARY = new BigDecimal("200000.00");

  public Salary {
    Objects.requireNonNull(amount, "Salary cannot be null");
    if (amount.compareTo(BigDecimal.ZERO) <= 0) {
      throw new InvalidSalaryException("Salary must be greater than zero");
    }
    if (amount.compareTo(MIN_ANNUAL_SALARY) < 0) {
      throw new InvalidSalaryException("Salary is below the legal or expected minimum");
    }
    if (amount.compareTo(MAX_ANNUAL_SALARY) > 0) {
      throw new InvalidSalaryException(
          "Salary exceeds the maximum allowed for annual gross salary");
    }
  }
}
