package com.antharos.corporateorganization.domain.jobtitle;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class JobTitle {

  private UUID id;

  private String description;

  private String photoUrl;

  private String createdBy;

  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;

  public JobTitle(UUID id) {
    this.id = id;
  }
}
