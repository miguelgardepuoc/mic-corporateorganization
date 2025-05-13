package com.antharos.corporateorganization.domain.employee.valueobject;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidEmployeeException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class EmployeeIdUnitTest {

  @Test
  void whenValidUUIDString_thenEmployeeIdIsCreated() {
    String validUUID = UUID.randomUUID().toString();
    EmployeeId employeeId = EmployeeId.of(validUUID);

    assertNotNull(employeeId);
    assertEquals(validUUID, employeeId.getValueAsString());
  }

  @Test
  void whenInvalidUUIDString_thenThrowInvalidDepartmentException() {
    String invalidUUID = "not-a-uuid";

    InvalidEmployeeException exception =
        assertThrows(InvalidEmployeeException.class, () -> EmployeeId.of(invalidUUID));

    assertTrue(exception.getMessage().contains("EmployeeId must be a valid UUID"));
    assertTrue(exception.getMessage().contains(invalidUUID));
  }
}
