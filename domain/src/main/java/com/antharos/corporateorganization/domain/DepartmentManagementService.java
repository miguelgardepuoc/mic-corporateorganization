package com.antharos.corporateorganization.domain;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.UsernameNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DepartmentManagementService {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;

  public void assignDepartmentHead(DepartmentId departmentId, String username, String changedBy) {
    Department department =
        departmentRepository
            .findBy(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(departmentId.toString()));

    Employee newHead =
        employeeRepository
            .findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException(username));

    Employee previousHead = department.getDepartmentHead();

    if (previousHead != null && !previousHead.equals(newHead)) {
      previousHead =
          employeeRepository
              .findById(previousHead.getId().getValueAsString())
              .orElseThrow(() -> new UsernameNotFoundException(""));
      previousHead.changeToEmployee(changedBy);
      employeeRepository.save(previousHead);
    }

    department.updateDepartmentHead(newHead, changedBy);
    departmentRepository.save(department);
    employeeRepository.save(newHead);
  }
}
