package com.antharos.corporateorganization.domain.department;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.globalexceptions.ConflictException;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
public class Department {
  private DepartmentId id;

  private String description;

  private Employee departmentHead;

  private boolean isActive;

  private String createdBy;

  private LocalDate createdAt;

  private String lastModifiedBy;

  private LocalDate lastModifiedAt;

  public Department(DepartmentId id) {
    this.id = id;
  }

  public Department(DepartmentId id, String description, boolean isActive, String createdBy) {
    this.id = id;
    this.description = description;
    this.isActive = isActive;
    this.createdBy = createdBy;
  }

  public static Department create(DepartmentId id, String description, String createdBy) {
    return new Department(id, description, true, createdBy);
  }

  public void rename(String description, String user) {
    if (!isActive()) {
      throw new ConflictException();
    }
    this.description = description;
    this.lastModifiedBy = user;
  }

  public void remove(String user) {
    if (!isActive()) {
      throw new ConflictException();
    }
    this.isActive = false;
    this.lastModifiedBy = user;
  }

  public boolean isActive() {
    return isActive;
  }
}
