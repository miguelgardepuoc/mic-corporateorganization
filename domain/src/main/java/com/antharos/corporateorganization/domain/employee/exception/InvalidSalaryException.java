package com.antharos.corporateorganization.domain.employee.exception;

public class InvalidSalaryException extends RuntimeException {
  public InvalidSalaryException(String message) {
    super(message);
  }
}
