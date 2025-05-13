package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidTelephoneException;
import org.junit.jupiter.api.Test;

class TelephoneNumberUnitTest {

  @Test
  void whenCreateTelephoneNumberWithValidValue_thenTelephoneNumberIsCreated() {
    TelephoneNumber number = new TelephoneNumber("+123456789012");
    assertEquals("+123456789012", number.value());
  }

  @Test
  void whenCreateTelephoneNumberWithValidPlainDigits_thenTelephoneNumberIsCreated() {
    TelephoneNumber number = new TelephoneNumber("987654321");
    assertEquals("987654321", number.value());
  }

  @Test
  void whenCreateTelephoneNumberWithNullValue_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> new TelephoneNumber(null));
  }

  @Test
  void whenCreateTelephoneNumberWithTooFewDigits_thenThrowInvalidTelephoneException() {
    assertThrows(InvalidTelephoneException.class, () -> new TelephoneNumber("12345"));
  }

  @Test
  void whenCreateTelephoneNumberWithTooManyDigits_thenThrowInvalidTelephoneException() {
    assertThrows(
        InvalidTelephoneException.class, () -> new TelephoneNumber("+1234567890123456789"));
  }

  @Test
  void whenCreateTelephoneNumberWithLetters_thenThrowInvalidTelephoneException() {
    assertThrows(InvalidTelephoneException.class, () -> new TelephoneNumber("+123ABC4567"));
  }

  @Test
  void whenCreateTelephoneNumberWithSpecialCharacters_thenThrowInvalidTelephoneException() {
    assertThrows(InvalidTelephoneException.class, () -> new TelephoneNumber("+123-456-7890"));
  }

  @Test
  void whenCreateTelephoneNumberWithPlusPrefix_thenTelephoneNumberIsCreated() {
    TelephoneNumber number = new TelephoneNumber("+491234567890");
    assertEquals("+491234567890", number.value());
  }

  @Test
  void whenCompareEqualTelephoneNumbers_thenTheyAreEqual() {
    TelephoneNumber a = new TelephoneNumber("+491234567890");
    TelephoneNumber b = new TelephoneNumber("+491234567890");

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void whenCompareDifferentTelephoneNumbers_thenTheyAreNotEqual() {
    TelephoneNumber a = new TelephoneNumber("123456789");
    TelephoneNumber b = new TelephoneNumber("987654321");

    assertNotEquals(a, b);
  }
}
