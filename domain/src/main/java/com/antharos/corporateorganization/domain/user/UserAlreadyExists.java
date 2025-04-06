package com.antharos.corporateorganization.domain.user;

import com.antharos.corporateorganization.domain.globalexceptions.AlreadyExistsException;

public class UserAlreadyExists extends AlreadyExistsException {
  public UserAlreadyExists() {
    super("User already exists");
  }
}
