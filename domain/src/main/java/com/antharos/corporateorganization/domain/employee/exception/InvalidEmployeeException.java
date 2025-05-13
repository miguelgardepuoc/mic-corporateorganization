package com.antharos.corporateorganization.domain.employee.exception;

public class InvalidEmployeeException extends RuntimeException {
  public InvalidEmployeeException(String message) {
    super(message);
  }
}
