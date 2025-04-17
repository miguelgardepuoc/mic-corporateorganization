package com.antharos.corporateorganization.application;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class LoginCommand {
  String username;
  String password;
}
