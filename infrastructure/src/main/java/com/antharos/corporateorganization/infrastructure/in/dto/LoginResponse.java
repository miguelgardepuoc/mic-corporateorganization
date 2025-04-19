package com.antharos.corporateorganization.infrastructure.in.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private String token;
  private long expiresIn;
}
