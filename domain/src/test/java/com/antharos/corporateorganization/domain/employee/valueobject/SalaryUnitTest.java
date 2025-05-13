package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidSalaryException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class SalaryUnitTest {

  @Test
  void whenCreateSalaryWithValidAmount_thenSalaryIsCreated() {
    BigDecimal validAmount = new BigDecimal("50000.00");

    Salary salary = new Salary(validAmount);

    assertEquals(validAmount, salary.amount());
  }

  @Test
  void whenCreateSalaryWithNullAmount_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> new Salary(null));
  }

  @Test
  void whenCreateSalaryWithZero_thenThrowInvalidSalaryException() {
    assertThrows(InvalidSalaryException.class, () -> new Salary(BigDecimal.ZERO));
  }

  @Test
  void whenCreateSalaryWithNegativeAmount_thenThrowInvalidSalaryException() {
    assertThrows(InvalidSalaryException.class, () -> new Salary(new BigDecimal("-1000.00")));
  }

  @Test
  void whenCreateSalaryBelowMinimum_thenThrowInvalidSalaryException() {
    assertThrows(InvalidSalaryException.class, () -> new Salary(new BigDecimal("19999.99")));
  }

  @Test
  void whenCreateSalaryAboveMaximum_thenThrowInvalidSalaryException() {
    assertThrows(InvalidSalaryException.class, () -> new Salary(new BigDecimal("200000.01")));
  }

  @Test
  void whenCreateSalaryAtMinimumBoundary_thenSalaryIsCreated() {
    BigDecimal min = new BigDecimal("20000.00");

    Salary salary = new Salary(min);

    assertEquals(min, salary.amount());
  }

  @Test
  void whenCreateSalaryAtMaximumBoundary_thenSalaryIsCreated() {
    BigDecimal max = new BigDecimal("200000.00");

    Salary salary = new Salary(max);

    assertEquals(max, salary.amount());
  }

  @Test
  void whenCompareEqualSalaries_thenTheyAreEqual() {
    Salary a = new Salary(new BigDecimal("60000.00"));
    Salary b = new Salary(new BigDecimal("60000.00"));

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void whenCompareDifferentSalaries_thenTheyAreNotEqual() {
    Salary a = new Salary(new BigDecimal("75000.00"));
    Salary b = new Salary(new BigDecimal("85000.00"));

    assertNotEquals(a, b);
  }
}
