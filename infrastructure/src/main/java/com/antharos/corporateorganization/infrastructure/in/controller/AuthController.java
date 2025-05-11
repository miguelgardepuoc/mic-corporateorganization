package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.commands.signup.SignUpCommand;
import com.antharos.corporateorganization.application.commands.signup.SignUpCommandHandler;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.RegisterUserRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Operations related to authentication and user sign-up")
public class AuthController {
  private final SignUpCommandHandler signUpCommandHandler;

  @PermitAll
  @PostMapping("/signup")
  @Operation(
      summary = "Register a new user",
      description = "Allows a new user to sign up by providing username and password.")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User registered successfully"),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid request",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(schema = @Schema()))
      })
  public ResponseEntity<Void> register(@RequestBody RegisterUserRequest request) {
    final SignUpCommand command =
        SignUpCommand.builder()
            .username(request.getUsername())
            .password(request.getPassword())
            .build();

    this.signUpCommandHandler.handle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
