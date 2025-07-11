package com.antharos.corporateorganization.infrastructure.out.repository.department;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentJpaRepository extends JpaRepository<DepartmentEntity, UUID> {
  List<DepartmentEntity> findAllByIsActiveTrue();
}
