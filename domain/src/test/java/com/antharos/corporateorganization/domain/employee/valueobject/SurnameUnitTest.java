package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidSurnameException;
import org.junit.jupiter.api.Test;

class SurnameUnitTest {

  @Test
  void whenCreateSurnameWithValidValue_thenSurnameIsCreated() {
    Surname surname = new Surname("García-López");

    assertEquals("García-López", surname.value());
  }

  @Test
  void whenCreateSurnameWithNullValue_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> new Surname(null));
  }

  @Test
  void whenCreateSurnameWithBlankValue_thenThrowInvalidSurnameException() {
    assertThrows(InvalidSurnameException.class, () -> new Surname(" "));
  }

  @Test
  void whenCreateSurnameWithOnlyNumbers_thenThrowInvalidSurnameException() {
    assertThrows(InvalidSurnameException.class, () -> new Surname("123456"));
  }

  @Test
  void whenCreateSurnameWithInvalidCharacters_thenThrowInvalidSurnameException() {
    assertThrows(InvalidSurnameException.class, () -> new Surname("Smith@Doe"));
  }

  @Test
  void whenCreateSurnameWithAccentsAndHyphens_thenSurnameIsCreated() {
    Surname surname = new Surname("Álvarez Núñez");

    assertEquals("Álvarez Núñez", surname.value());
  }

  @Test
  void whenCreateSurnameWithValidSpecialCharacters_thenSurnameIsCreated() {
    Surname surname = new Surname("O'Connor");

    assertEquals("O'Connor", surname.value());
  }

  @Test
  void whenCompareEqualSurnames_thenTheyAreEqual() {
    Surname a = new Surname("Fernández");
    Surname b = new Surname("Fernández");

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void whenCompareDifferentSurnames_thenTheyAreNotEqual() {
    Surname a = new Surname("Rodríguez");
    Surname b = new Surname("Martínez");

    assertNotEquals(a, b);
  }
}
