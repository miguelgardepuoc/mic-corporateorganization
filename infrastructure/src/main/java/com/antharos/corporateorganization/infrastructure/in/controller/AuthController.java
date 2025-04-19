package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.commands.login.LoginCommand;
import com.antharos.corporateorganization.application.commands.login.LoginCommandHandler;
import com.antharos.corporateorganization.application.commands.signup.SignUpCommand;
import com.antharos.corporateorganization.application.commands.signup.SignUpCommandHandler;
import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.infrastructure.in.dto.LoginRequest;
import com.antharos.corporateorganization.infrastructure.in.dto.LoginResponse;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.RegisterUserRequest;
import com.antharos.corporateorganization.infrastructure.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
  private final LoginCommandHandler loginCommandHandler;
  private final SignUpCommandHandler signUpCommandHandler;
  private final JwtService jwtService;

  @PostMapping("/signup")
  public ResponseEntity<Void> register(@RequestBody RegisterUserRequest request) {
    final SignUpCommand command =
        SignUpCommand.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();
    this.signUpCommandHandler.handle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
    final LoginCommand command =
        LoginCommand.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();

    final User authenticatedUser = this.loginCommandHandler.handle(command);

    final String jwtToken = this.jwtService.generateToken(authenticatedUser);

    final LoginResponse loginResponse = new LoginResponse();
    loginResponse.setToken(jwtToken);
    loginResponse.setExpiresIn(this.jwtService.getExpirationTime());

    return ResponseEntity.ok(loginResponse);
  }
}
