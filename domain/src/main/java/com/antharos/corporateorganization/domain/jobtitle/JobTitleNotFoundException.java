package com.antharos.corporateorganization.domain.jobtitle;

import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;

public class JobTitleNotFoundException extends NotFoundException {
  public JobTitleNotFoundException(String jobTitleId) {
    super("JobTitle not found with ID: " + jobTitleId);
  }
}
