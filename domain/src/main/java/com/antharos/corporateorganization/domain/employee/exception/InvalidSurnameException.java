package com.antharos.corporateorganization.domain.employee.exception;

public class InvalidSurnameException extends RuntimeException {
  public InvalidSurnameException(String message) {
    super(message);
  }
}
