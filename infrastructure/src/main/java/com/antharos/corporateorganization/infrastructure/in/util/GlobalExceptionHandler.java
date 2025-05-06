package com.antharos.corporateorganization.infrastructure.in.util;

import com.antharos.corporateorganization.domain.employee.exception.*;
import com.antharos.corporateorganization.domain.globalexceptions.AlreadyExistsException;
import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler({IllegalArgumentException.class, NotFoundException.class})
  public ResponseEntity<String> handleIllegalArgumentException(RuntimeException ex) {
    log.error("IllegalArgumentException thrown: ", ex);
    return new ResponseEntity<>("Invalid request data: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(AlreadyExistsException.class)
  public ResponseEntity<String> handleAlreadyExistsException(AlreadyExistsException ex) {
    log.warn("Resource already exists exception thrown: ", ex);
    return new ResponseEntity<>("Resource already exists: " + ex.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(InvalidDepartmentException.class)
  public ResponseEntity<ErrorResponse> handleInvalidDepartment(InvalidDepartmentException ex) {
    return buildErrorResponse("department", "INVALID_DEPARTMENT", ex.getMessage());
  }

  @ExceptionHandler(InvalidDniException.class)
  public ResponseEntity<ErrorResponse> handleInvalidDni(InvalidDniException ex) {
    return buildErrorResponse("dni", "INVALID_DNI", ex.getMessage());
  }

  @ExceptionHandler(InvalidHiringDateException.class)
  public ResponseEntity<ErrorResponse> handleInvalidHiringDate(InvalidHiringDateException ex) {
    return buildErrorResponse("hiringDate", "INVALID_HIRING_DATE", ex.getMessage());
  }

  @ExceptionHandler(InvalidJobTitleException.class)
  public ResponseEntity<ErrorResponse> handleInvalidJobTitle(InvalidJobTitleException ex) {
    return buildErrorResponse("jobTitle", "INVALID_JOB_TITLE", ex.getMessage());
  }

  @ExceptionHandler(InvalidNameException.class)
  public ResponseEntity<ErrorResponse> handleInvalidName(InvalidNameException ex) {
    return buildErrorResponse("name", "INVALID_NAME", ex.getMessage());
  }

  @ExceptionHandler(InvalidSalaryException.class)
  public ResponseEntity<ErrorResponse> handleInvalidSalary(InvalidSalaryException ex) {
    return buildErrorResponse("salary", "INVALID_SALARY", ex.getMessage());
  }

  @ExceptionHandler(InvalidSurnameException.class)
  public ResponseEntity<ErrorResponse> handleInvalidSurname(InvalidSurnameException ex) {
    return buildErrorResponse("surname", "INVALID_SURNAME", ex.getMessage());
  }

  @ExceptionHandler(InvalidTelephoneException.class)
  public ResponseEntity<ErrorResponse> handleInvalidPhone(InvalidTelephoneException ex) {
    return buildErrorResponse("telephone", "INVALID_PHONE_FORMAT", ex.getMessage());
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
      String field, String code, String message) {
    ErrorResponse errorResponse = new ErrorResponse(List.of(new FieldError(field, code, message)));
    return ResponseEntity.badRequest().body(errorResponse);
  }
}
