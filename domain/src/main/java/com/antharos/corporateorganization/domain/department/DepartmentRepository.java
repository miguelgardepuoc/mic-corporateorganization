package com.antharos.corporateorganization.domain.department;

import java.util.Optional;

public interface DepartmentRepository {
  Optional<Department> findBy(DepartmentId departmentId);
}
