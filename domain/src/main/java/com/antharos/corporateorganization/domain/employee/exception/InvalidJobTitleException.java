package com.antharos.corporateorganization.domain.employee.exception;

public class InvalidJobTitleException extends RuntimeException {
  public InvalidJobTitleException(String message) {
    super(message);
  }
}
