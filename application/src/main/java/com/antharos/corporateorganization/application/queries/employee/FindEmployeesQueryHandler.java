package com.antharos.corporateorganization.application.queries.employee;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeesQueryHandler {
  private final UserRepository userRepository;

  public List<Employee> handle() {
    return this.userRepository.findAll();
  }
}
