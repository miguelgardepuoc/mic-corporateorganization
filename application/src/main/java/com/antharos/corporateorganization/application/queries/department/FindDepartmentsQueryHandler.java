package com.antharos.corporateorganization.application.queries.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindDepartmentsQueryHandler {
  private final DepartmentRepository departmentRepository;

  public List<Department> handle() {
    return this.departmentRepository.findAllActive();
  }
}
