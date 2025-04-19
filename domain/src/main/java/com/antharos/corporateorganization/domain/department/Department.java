package com.antharos.corporateorganization.domain.department;

import com.antharos.corporateorganization.domain.globalexceptions.ConflictException;
import com.antharos.corporateorganization.domain.user.User;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Department {
  private DepartmentId id;

  private String description;

  private User departmentHead;

  private boolean isActive;

  private String createdBy;

  private Date createdAt;

  private String lastModifiedBy;

  private Date lastModifiedAt;

  public Department(DepartmentId id) {
    this.id = id;
  }

  public Department(
      DepartmentId id, String description, boolean isActive, Date createdAt, String createdBy) {
    this.id = id;
    this.description = description;
    this.isActive = isActive;
    this.createdAt = createdAt;
    this.createdBy = createdBy;
  }

  public static Department create(DepartmentId id, String description, String createdBy) {
    return new Department(id, description, true, new Date(), createdBy);
  }

  public void rename(String description, String user) {
    if (!isActive()) {
      throw new ConflictException();
    }
    this.description = description;
    this.lastModifiedAt = new Date();
    this.lastModifiedBy = user;
  }

  public void remove(String user) {
    if (!isActive()) {
      throw new ConflictException();
    }
    this.isActive = false;
    this.lastModifiedAt = new Date();
    this.lastModifiedBy = user;
  }

  public boolean isActive() {
    return isActive;
  }
}
