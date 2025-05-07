package com.antharos.corporateorganization.application.queries.employee;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class FindEmployeeByIdQueryHandler {
  private final UserRepository userRepository;

  public Optional<Employee> handle(FindEmployeeByIdQuery query) {
    return this.userRepository.findById(query.getId());
  }
}
