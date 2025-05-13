package com.antharos.corporateorganization.domain.employee.exception;

import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;

public class UsernameNotFoundException extends NotFoundException {
  public UsernameNotFoundException(String message) {
    super(message);
  }
}
