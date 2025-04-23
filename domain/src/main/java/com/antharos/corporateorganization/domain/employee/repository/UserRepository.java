package com.antharos.corporateorganization.domain.employee.repository;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
  Optional<Employee> findBy(EmployeeId employeeId);

  Optional<Employee> findByUsername(String username);

  void save(Employee employee);

  boolean usernameExists(String username);

  Optional<Employee> findTopByOrderByEmployeeNumberDesc();

  List<Employee> findAll();
}
