package com.antharos.corporateorganization.domain.employee.exception;

public class InvalidDniException extends IllegalArgumentException {
  public InvalidDniException(String message) {
    super(message);
  }
}
