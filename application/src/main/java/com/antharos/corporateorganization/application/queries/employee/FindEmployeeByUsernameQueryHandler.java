package com.antharos.corporateorganization.application.queries.employee;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeeByUsernameQueryHandler {
  private final UserRepository userRepository;

  public Optional<Employee> handle(FindEmployeeByUsernameQuery query) {
    return this.userRepository.findByUsername(query.getUsername());
  }
}
