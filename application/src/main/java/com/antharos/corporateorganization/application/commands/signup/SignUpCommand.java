package com.antharos.corporateorganization.application.commands.signup;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class SignUpCommand {
  String username;
  String password;
}
