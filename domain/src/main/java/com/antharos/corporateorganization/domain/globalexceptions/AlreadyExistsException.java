package com.antharos.corporateorganization.domain.globalexceptions;

public class AlreadyExistsException extends RuntimeException {
  public AlreadyExistsException(String message) {
    super(message);
  }
}
