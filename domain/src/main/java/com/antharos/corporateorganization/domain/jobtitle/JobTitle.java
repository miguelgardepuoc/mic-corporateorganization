package com.antharos.corporateorganization.domain.jobtitle;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class JobTitle {

  private JobTitleId id;

  private String description;

  private String photoUrl;

  private String createdBy;

  private LocalDate createdAt;

  private String lastModifiedBy;

  private LocalDate lastModifiedAt;

  public JobTitle(JobTitleId id) {
    this.id = id;
  }
}
