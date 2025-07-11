package com.antharos.corporateorganization.application.queries.employee;

import static com.antharos.corporateorganization.domain.employee.Status.ACTIVE;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeeByUsernameQueryHandler {
  private final EmployeeRepository employeeRepository;

  public Optional<Employee> handle(FindEmployeeByUsernameQuery query) {
    return this.employeeRepository
        .findByUsername(query.getUsername())
        .filter(employee -> ACTIVE.equals(employee.getStatus()));
  }
}
