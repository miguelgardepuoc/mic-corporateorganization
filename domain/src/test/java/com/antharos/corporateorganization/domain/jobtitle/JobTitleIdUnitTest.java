package com.antharos.corporateorganization.domain.jobtitle;

import static org.junit.jupiter.api.Assertions.*;

import com.antharos.corporateorganization.domain.employee.exception.InvalidJobTitleException;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JobTitleIdUnitTest {

  @Test
  void whenValidUUIDString_thenJobTitleIdIsCreated() {
    String validUUID = UUID.randomUUID().toString();
    JobTitleId jobTitleId = JobTitleId.of(validUUID);

    assertNotNull(jobTitleId);
    assertEquals(validUUID, jobTitleId.getValueAsString());
  }

  @Test
  void whenInvalidUUIDString_thenThrowInvalidDepartmentException() {
    String invalidUUID = "not-a-uuid";

    InvalidJobTitleException exception =
        assertThrows(InvalidJobTitleException.class, () -> JobTitleId.of(invalidUUID));

    assertTrue(exception.getMessage().contains("JobTitleId must be a valid UUID"));
    assertTrue(exception.getMessage().contains(invalidUUID));
  }
}
