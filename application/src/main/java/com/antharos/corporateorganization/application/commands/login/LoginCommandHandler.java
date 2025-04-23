package com.antharos.corporateorganization.application.commands.login;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginCommandHandler {
  private final UserRepository userRepository;

  private final AuthenticationManager authenticationManager;

  public Employee handle(LoginCommand command) {
    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));

    return this.userRepository
        .findByUsername(command.getUsername())
        .orElseThrow(() -> new BadCredentialsException(command.getUsername()));
  }
}
