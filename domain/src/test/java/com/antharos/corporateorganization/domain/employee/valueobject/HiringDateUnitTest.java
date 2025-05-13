package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class HiringDateUnitTest {

  @Test
  void whenCreateHiringDateWithValidDate_thenHiringDateIsCreated() {
    LocalDate date = LocalDate.of(2024, 1, 1);

    HiringDate hiringDate = new HiringDate(date);

    assertEquals(date, hiringDate.value());
  }

  @Test
  void whenCreateHiringDateWithNullDate_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> new HiringDate(null));
  }

  @Test
  void whenCompareEqualHiringDates_thenTheyAreEqual() {
    LocalDate date = LocalDate.of(2023, 5, 10);

    HiringDate a = new HiringDate(date);
    HiringDate b = new HiringDate(date);

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void whenCompareDifferentHiringDates_thenTheyAreNotEqual() {
    HiringDate a = new HiringDate(LocalDate.of(2023, 1, 1));
    HiringDate b = new HiringDate(LocalDate.of(2024, 1, 1));

    assertNotEquals(a, b);
  }
}
