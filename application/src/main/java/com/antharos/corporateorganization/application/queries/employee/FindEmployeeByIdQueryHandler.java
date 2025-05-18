package com.antharos.corporateorganization.application.queries.employee;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeeByIdQueryHandler {
  private final EmployeeRepository employeeRepository;

  public Optional<Employee> handle(FindEmployeeByIdQuery query) {
    return this.employeeRepository.findById(query.getId());
  }
}
