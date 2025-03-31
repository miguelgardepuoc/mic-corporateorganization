package com.antharos.corporateorganization.infrastructure.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, UUID> {
  List<UserEntity> findByDepartment(DepartmentEntity department);
}
