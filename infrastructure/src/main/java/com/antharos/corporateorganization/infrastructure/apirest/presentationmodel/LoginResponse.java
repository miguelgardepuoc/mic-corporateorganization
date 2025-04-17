package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private String token;
  private long expiresIn;
}
