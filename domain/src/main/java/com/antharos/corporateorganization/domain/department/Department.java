package com.antharos.corporateorganization.domain.department;

import com.antharos.corporateorganization.domain.user.User;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Department {
  private UUID id;

  private String description;

  private User departmentHead;

  private String createdBy;

  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;

  public Department(UUID id) {
    this.id = id;
  }
}
