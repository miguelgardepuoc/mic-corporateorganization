package com.antharos.corporateorganization.infrastructure.repository.user;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.UserId;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import com.antharos.corporateorganization.infrastructure.repository.department.DepartmentEntityMapper;
import com.antharos.corporateorganization.infrastructure.repository.jobtitle.JobTitleEntityMapper;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepository {

  private final UserJpaRepository userJpaRepository;
  private final UserEntityMapper mapper;
  private final DepartmentEntityMapper departmentEntityMapper;
  private final JobTitleEntityMapper jobTitleEntityMapper;

  @Override
  public Optional<User> findBy(UserId userId) {
    return this.userJpaRepository
        .findById(UUID.fromString(userId.getValueAsString()))
        .map(this.mapper::toDomain);
  }

  @Override
  public void save(User user) {
    this.userJpaRepository.save(
        this.mapper.toEntity(user, this.departmentEntityMapper, this.jobTitleEntityMapper));
  }

  @Override
  public boolean usernameExists(String username) {
    return this.userJpaRepository.existsByUsername(username);
  }

  @Override
  public boolean existsByEmployeeNumber(Long employeeNumber) {
    return this.userJpaRepository.existsByEmployeeNumber(employeeNumber);
  }
}
