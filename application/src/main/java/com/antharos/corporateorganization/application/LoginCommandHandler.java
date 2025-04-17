package com.antharos.corporateorganization.application;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
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

  public User handle(LoginCommand command) {
    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(command.getUsername(), command.getPassword()));

    return this.userRepository
        .findByUsername(command.getUsername())
        .orElseThrow(() -> new BadCredentialsException(command.getUsername()));
  }
}
