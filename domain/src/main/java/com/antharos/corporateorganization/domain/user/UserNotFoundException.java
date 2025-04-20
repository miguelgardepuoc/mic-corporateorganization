package com.antharos.corporateorganization.domain.user;

import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;

public class UserNotFoundException extends NotFoundException {
  public UserNotFoundException(String userId) {
    super("User not found with ID: " + userId);
  }
}
