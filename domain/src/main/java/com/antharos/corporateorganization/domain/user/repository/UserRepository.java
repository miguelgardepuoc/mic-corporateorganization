package com.antharos.corporateorganization.domain.user.repository;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
  Optional<User> findBy(UserId userId);

  Optional<User> findByUsername(String username);

  void save(User user);

  boolean usernameExists(String username);

  boolean existsByEmployeeNumber(Long employeeNumber);

  Optional<User> findTopByOrderByEmployeeNumberDesc();

    List<User> findAll();
}
