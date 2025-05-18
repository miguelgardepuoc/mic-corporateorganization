package com.antharos.corporateorganization.application.commands.signup;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpCommandHandler {
  private final EmployeeRepository employeeRepository;
  private final PasswordEncoder passwordEncoder;

  public void handle(SignUpCommand command) {
    Employee employee =
        this.employeeRepository
            .findByUsername(command.getUsername())
            .orElseThrow(() -> new BadCredentialsException(command.getUsername()));
    employee.signup(this.passwordEncoder.encode(command.getPassword()), command.getUsername());

    this.employeeRepository.save(employee);
  }
}
