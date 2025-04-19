package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.employee;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserRequest {
  private String username;
  private String password;
}
