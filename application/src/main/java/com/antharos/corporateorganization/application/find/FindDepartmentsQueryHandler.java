package com.antharos.corporateorganization.application.find;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FindDepartmentsQueryHandler {
  private final DepartmentRepository departmentRepository;

  public List<Department> handle(FindDepartmentsQuery query) {
    return this.departmentRepository.findAll();
  }
}
