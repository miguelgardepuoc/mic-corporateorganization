package com.antharos.corporateorganization.domain.jobtitle;

import com.antharos.corporateorganization.domain.employee.exception.InvalidJobTitleException;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JobTitleId {

  String valueAsString;

  public static JobTitleId of(String jobTitleId) {
    try {
      UUID.fromString(jobTitleId);
    } catch (IllegalArgumentException e) {
      throw new InvalidJobTitleException(
          "JobTitleId must be a valid UUID. Invalid string value: " + jobTitleId);
    }
    return new JobTitleId(jobTitleId);
  }
}
