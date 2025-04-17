package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel;

import lombok.Getter;

@Getter
public class LoginRequest {
  String username;
  String password;
}
