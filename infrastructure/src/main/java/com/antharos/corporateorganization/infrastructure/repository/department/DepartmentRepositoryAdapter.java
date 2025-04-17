package com.antharos.corporateorganization.infrastructure.repository.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryAdapter implements DepartmentRepository {

  private final DepartmentJpaRepository departmentJpaRepository;
  private final DepartmentEntityMapper mapper;

  @Override
  public Optional<Department> findBy(DepartmentId departmentId) {
    return this.departmentJpaRepository
        .findById(UUID.fromString(departmentId.getValueAsString()))
        .map(this.mapper::toDomain);
  }
}
