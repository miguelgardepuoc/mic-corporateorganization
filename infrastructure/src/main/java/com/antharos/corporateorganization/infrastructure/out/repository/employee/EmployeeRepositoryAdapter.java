package com.antharos.corporateorganization.infrastructure.out.repository.employee;

import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import com.antharos.corporateorganization.infrastructure.out.repository.department.DepartmentEntityMapper;
import com.antharos.corporateorganization.infrastructure.out.repository.jobtitle.JobTitleEntityMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmployeeRepositoryAdapter implements UserRepository {

  private final EmployeeJpaRepository employeeJpaRepository;
  private final EmployeeEntityMapper mapper;
  private final DepartmentEntityMapper departmentEntityMapper;
  private final JobTitleEntityMapper jobTitleEntityMapper;

  @Override
  public Optional<Employee> findBy(EmployeeId employeeId) {
    return this.employeeJpaRepository
        .findById(UUID.fromString(employeeId.getValueAsString()))
        .map(this.mapper::toDomain);
  }

  @Override
  public Optional<Employee> findByUsername(String username) {
    return this.employeeJpaRepository.findByUsername(username).map(this.mapper::toDomain);
  }

  @Override
  public void save(Employee employee) {
    this.employeeJpaRepository.save(
        this.mapper.toEntity(employee, this.departmentEntityMapper, this.jobTitleEntityMapper));
  }

  @Override
  public boolean usernameExists(String username) {
    return this.employeeJpaRepository.existsByUsername(username);
  }

  @Override
  public Optional<Employee> findTopByOrderByEmployeeNumberDesc() {
    return this.employeeJpaRepository
        .findTopByOrderByEmployeeNumberDesc()
        .map(this.mapper::toDomain);
  }

  @Override
  public List<Employee> findAll() {
    return this.employeeJpaRepository.findAll().stream().map(this.mapper::toDomain).toList();
  }

  @Override
  public List<Employee> findByDepartmentId(DepartmentId departmentId) {
    return this.employeeJpaRepository
        .findByDepartmentId(UUID.fromString(departmentId.getValueAsString()))
        .stream()
        .map(this.mapper::toDomain)
        .toList();
  }

  @Override
  public Optional<Employee> findById(String id) {
    return this.employeeJpaRepository.findById(UUID.fromString(id)).map(this.mapper::toDomain);
  }
}
