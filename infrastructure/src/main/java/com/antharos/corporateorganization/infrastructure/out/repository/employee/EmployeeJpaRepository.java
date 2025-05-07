package com.antharos.corporateorganization.infrastructure.out.repository.employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, UUID> {
  boolean existsByUsername(String username);

  Optional<EmployeeEntity> findByUsername(String username);

  Optional<EmployeeEntity> findTopByOrderByEmployeeNumberDesc();

  List<EmployeeEntity> findByDepartmentId(UUID departmentId);
}
