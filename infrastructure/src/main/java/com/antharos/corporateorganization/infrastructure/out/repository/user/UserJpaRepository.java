package com.antharos.corporateorganization.infrastructure.out.repository.user;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, UUID> {
  boolean existsByUsername(String username);

  Optional<UserEntity> findByUsername(String username);

  Optional<UserEntity> findTopByOrderByEmployeeNumberDesc();
}
