package com.antharos.corporateorganization.domain.department;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_EMPLOYEE;
import static com.antharos.corporateorganization.domain.employee.Status.ACTIVE;

import com.antharos.corporateorganization.domain.department.exception.DepartmentHasActiveEmployeesException;
import com.antharos.corporateorganization.domain.department.exception.EmployeeOfDifferentDepartmentException;
import com.antharos.corporateorganization.domain.department.exception.NotActiveUserException;
import com.antharos.corporateorganization.domain.department.exception.NotEmployeeException;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
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

  public void remove(String user, EmployeeRepository employeeRepository) {
    if (!isActive()) {
      throw new ConflictException();
    }
    if (employeeRepository.hasActiveEmployeesByDepartmentId(this.getId())) {
      throw new DepartmentHasActiveEmployeesException(this.getId().getValueAsString());
    }
    this.isActive = false;
    this.lastModifiedBy = user;
  }

  public boolean isActive() {
    return isActive;
  }

  public void updateDepartmentHead(
      Employee departmentHead, String user, EmployeeRepository employeeRepository) {
    if (!isActive()) {
      throw new ConflictException();
    }
    if (!ACTIVE.equals(departmentHead.getStatus())) {
      throw new NotActiveUserException();
    }
    if (!ROLE_EMPLOYEE.equals(departmentHead.getRole())) {
      throw new NotEmployeeException();
    }
    if (departmentHead.getDepartment().equals(this)) {
      throw new EmployeeOfDifferentDepartmentException();
    }
    departmentHead.changeToDepartmentHead(user);
    this.departmentHead = departmentHead;
    this.lastModifiedBy = user;
  }
}
