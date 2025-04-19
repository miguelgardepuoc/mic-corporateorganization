package com.antharos.corporateorganization.application.commands.signup;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
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
    User user =
        this.userRepository
            .findByUsername(command.getUsername())
            .orElseThrow(() -> new BadCredentialsException(command.getUsername()));
    user.signup(this.passwordEncoder.encode(command.getPassword()));

    this.userRepository.save(user);
  }
}
