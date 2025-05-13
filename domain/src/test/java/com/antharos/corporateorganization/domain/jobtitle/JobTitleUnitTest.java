package com.antharos.corporateorganization.domain.jobtitle;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;

class JobTitleUnitTest {

  @Test
  void whenConstructJobTitleWithAllFields_thenFieldsAreAssignedCorrectly() {
    JobTitleId id = JobTitleId.of(UUID.randomUUID().toString());
    String description = "Software Engineer";
    String photoUrl = "http://example.com/photo.png";
    String createdBy = "admin";
    LocalDate createdAt = LocalDate.now();
    String lastModifiedBy = "hr";
    LocalDate lastModifiedAt = LocalDate.now();

    JobTitle jobTitle =
        new JobTitle(
            id, description, photoUrl, createdBy, createdAt, lastModifiedBy, lastModifiedAt);

    assertEquals(id, jobTitle.getId());
    assertEquals(description, jobTitle.getDescription());
    assertEquals(photoUrl, jobTitle.getPhotoUrl());
    assertEquals(createdBy, jobTitle.getCreatedBy());
    assertEquals(createdAt, jobTitle.getCreatedAt());
    assertEquals(lastModifiedBy, jobTitle.getLastModifiedBy());
    assertEquals(lastModifiedAt, jobTitle.getLastModifiedAt());
  }

  @Test
  void whenConstructJobTitleWithOnlyId_thenOnlyIdIsAssigned() {
    JobTitleId id = JobTitleId.of(UUID.randomUUID().toString());

    JobTitle jobTitle = new JobTitle(id);

    assertEquals(id, jobTitle.getId());
    assertNull(jobTitle.getDescription());
    assertNull(jobTitle.getPhotoUrl());
    assertNull(jobTitle.getCreatedBy());
    assertNull(jobTitle.getCreatedAt());
    assertNull(jobTitle.getLastModifiedBy());
    assertNull(jobTitle.getLastModifiedAt());
  }

  @Test
  void whenCompareEqualJobTitleIds_thenObjectsAreEqual() {
    JobTitleId id = JobTitleId.of(UUID.randomUUID().toString());

    JobTitle a = new JobTitle(id);
    JobTitle b = new JobTitle(id);

    assertEquals(a, b);
    assertEquals(a.hashCode(), b.hashCode());
  }

  @Test
  void whenCompareDifferentJobTitleIds_thenObjectsAreNotEqual() {
    JobTitle a = new JobTitle(JobTitleId.of(UUID.randomUUID().toString()));
    JobTitle b = new JobTitle(JobTitleId.of(UUID.randomUUID().toString()));

    assertNotEquals(a, b);
  }
}
