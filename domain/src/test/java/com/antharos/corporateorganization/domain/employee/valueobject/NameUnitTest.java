package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidNameException;
import org.junit.jupiter.api.Test;

class NameUnitTest {

  @Test
  void whenCreateNameWithValidString_thenNameIsCreated() {
    String validName = "José Luis";

    Name name = new Name(validName);

    assertEquals(validName, name.value());
  }

  @Test
  void whenCreateNameWithNull_thenThrowNullPointerException() {
    assertThrows(NullPointerException.class, () -> new Name(null));
  }

  @Test
  void whenCreateNameWithBlankString_thenThrowInvalidNameException() {
    assertThrows(InvalidNameException.class, () -> new Name("   "));
  }

  @Test
  void whenCreateNameWithOnlyNumbers_thenThrowInvalidNameException() {
    assertThrows(InvalidNameException.class, () -> new Name("12345"));
  }

  @Test
  void whenCreateNameWithInvalidCharacters_thenThrowInvalidNameException() {
    assertThrows(InvalidNameException.class, () -> new Name("John@Doe"));
    assertThrows(InvalidNameException.class, () -> new Name("Jane#"));
    assertThrows(InvalidNameException.class, () -> new Name("Tom123"));
  }

  @Test
  void whenCompareEqualNames_thenTheyAreEqual() {
    Name a = new Name("María");
    Name b = new Name("María");

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void whenCompareDifferentNames_thenTheyAreNotEqual() {
    Name a = new Name("Ana");
    Name b = new Name("Clara");

    assertNotEquals(a, b);
  }
}
