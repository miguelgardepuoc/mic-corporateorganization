package com.antharos.corporateorganization.infrastructure.repository.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DepartmentRepositoryAdapter implements DepartmentRepository {

  private final DepartmentJpaRepository jpaRepository;
  private final DepartmentEntityMapper mapper;

  @Override
  public Optional<Department> findBy(DepartmentId departmentId) {
    return this.jpaRepository
        .findById(UUID.fromString(departmentId.getValueAsString()))
        .map(this.mapper::toDomain);
  }

  @Override
  public List<Department> findAllActive() {
    return this.jpaRepository.findAllByIsActiveTrue().stream().map(this.mapper::toDomain).toList();
  }

  @Override
  public void save(Department department) {
    this.jpaRepository.save(this.mapper.toEntity(department));
  }
}
