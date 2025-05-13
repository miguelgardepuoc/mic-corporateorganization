package com.antharos.corporateorganization.application.commands.signup;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SignUpCommandHandler {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void handle(SignUpCommand command) {
    Employee employee =
        this.userRepository
            .findByUsername(command.getUsername())
            .orElseThrow(() -> new BadCredentialsException(command.getUsername()));
    employee.signup(this.passwordEncoder.encode(command.getPassword()), command.getUsername());

    this.userRepository.save(employee);
  }
}
