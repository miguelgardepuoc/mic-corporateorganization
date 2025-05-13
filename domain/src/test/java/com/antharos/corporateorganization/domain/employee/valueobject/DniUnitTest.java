package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidDniException;
import org.junit.jupiter.api.Test;

class DniUnitTest {

  @Test
  void whenCreateDniWithValidFormat_thenDniIsCreatedSuccessfully() {
    String validDni = "12345678A";

    Dni dni = Dni.of(validDni);

    assertEquals(validDni, dni.getValueAsString());
  }

  @Test
  void whenCreateDniWithInvalidFormat_thenThrowInvalidDniException() {
    String invalidDni1 = "1234567A"; // Too short
    String invalidDni2 = "ABCDEFGHJ"; // No digits
    String invalidDni3 = "123456789"; // No letter
    String invalidDni4 = "12345678AA"; // Too long

    assertThrows(InvalidDniException.class, () -> Dni.of(invalidDni1));
    assertThrows(InvalidDniException.class, () -> Dni.of(invalidDni2));
    assertThrows(InvalidDniException.class, () -> Dni.of(invalidDni3));
    assertThrows(InvalidDniException.class, () -> Dni.of(invalidDni4));
  }

  @Test
  void whenCreateDniWithNullValue_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> Dni.of(null));
  }

  @Test
  void whenCompareEqualDniObjects_thenTheyAreEqual() {
    Dni dni1 = Dni.of("12345678Z");
    Dni dni2 = Dni.of("12345678Z");

    assertEquals(dni1, dni2);
    assertEquals(dni1.hashCode(), dni2.hashCode());
  }

  @Test
  void whenCompareDifferentDniObjects_thenTheyAreNotEqual() {
    Dni dni1 = Dni.of("12345678X");
    Dni dni2 = Dni.of("87654321Y");

    assertNotEquals(dni1, dni2);
  }
}
