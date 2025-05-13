package com.antharos.corporateorganization.domain.department;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidDepartmentException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class DepartmentIdUnitTest {

  @Test
  void whenValidUUIDString_thenDepartmentIdIsCreated() {
    String validUUID = UUID.randomUUID().toString();
    DepartmentId departmentId = DepartmentId.of(validUUID);

    assertNotNull(departmentId);
    assertEquals(validUUID, departmentId.getValueAsString());
  }

  @Test
  void whenInvalidUUIDString_thenThrowInvalidDepartmentException() {
    String invalidUUID = "not-a-uuid";

    InvalidDepartmentException exception =
        assertThrows(InvalidDepartmentException.class, () -> DepartmentId.of(invalidUUID));

    assertTrue(exception.getMessage().contains("DepartmentId must be a valid UUID"));
    assertTrue(exception.getMessage().contains(invalidUUID));
  }
}
