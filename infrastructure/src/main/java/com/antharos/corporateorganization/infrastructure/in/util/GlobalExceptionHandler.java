package com.antharos.corporateorganization.infrastructure.in.util;

import com.antharos.corporateorganization.domain.department.exception.NotActiveUserException;
import com.antharos.corporateorganization.domain.department.exception.NotEmployeeException;
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
    return buildBadRequestErrorResponse("department", "INVALID_DEPARTMENT", ex.getMessage());
  }

  @ExceptionHandler(InvalidDniException.class)
  public ResponseEntity<ErrorResponse> handleInvalidDni(InvalidDniException ex) {
    return buildBadRequestErrorResponse("dni", "INVALID_DNI", ex.getMessage());
  }

  @ExceptionHandler(InvalidHiringDateException.class)
  public ResponseEntity<ErrorResponse> handleInvalidHiringDate(InvalidHiringDateException ex) {
    return buildBadRequestErrorResponse("hiringDate", "INVALID_HIRING_DATE", ex.getMessage());
  }

  @ExceptionHandler(InvalidJobTitleException.class)
  public ResponseEntity<ErrorResponse> handleInvalidJobTitle(InvalidJobTitleException ex) {
    return buildBadRequestErrorResponse("jobTitle", "INVALID_JOB_TITLE", ex.getMessage());
  }

  @ExceptionHandler(InvalidNameException.class)
  public ResponseEntity<ErrorResponse> handleInvalidName(InvalidNameException ex) {
    return buildBadRequestErrorResponse("name", "INVALID_NAME", ex.getMessage());
  }

  @ExceptionHandler(InvalidSalaryException.class)
  public ResponseEntity<ErrorResponse> handleInvalidSalary(InvalidSalaryException ex) {
    return buildBadRequestErrorResponse("salary", "INVALID_SALARY", ex.getMessage());
  }

  @ExceptionHandler(InvalidSurnameException.class)
  public ResponseEntity<ErrorResponse> handleInvalidSurname(InvalidSurnameException ex) {
    return buildBadRequestErrorResponse("surname", "INVALID_SURNAME", ex.getMessage());
  }

  @ExceptionHandler(InvalidTelephoneException.class)
  public ResponseEntity<ErrorResponse> handleInvalidPhone(InvalidTelephoneException ex) {
    return buildBadRequestErrorResponse("telephone", "INVALID_PHONE_FORMAT", ex.getMessage());
  }

  @ExceptionHandler(NotActiveUserException.class)
  public ResponseEntity<ErrorResponse> handleNotActiveUserException(NotActiveUserException ex) {
    return buildUnprocessableEntityErrorResponse(
        "departmentHead", "USER_IS_NOT_ACTIVE", ex.getMessage());
  }

  @ExceptionHandler(NotEmployeeException.class)
  public ResponseEntity<ErrorResponse> handleNotEmployeeException(NotEmployeeException ex) {
    return buildUnprocessableEntityErrorResponse(
        "departmentHead", "USER_IS_NOT_EMPLOYEE", ex.getMessage());
  }

  private ResponseEntity<ErrorResponse> buildUnprocessableEntityErrorResponse(
      String field, String code, String message) {
    return buildErrorResponse(field, code, message, HttpStatus.UNPROCESSABLE_ENTITY);
  }

  private ResponseEntity<ErrorResponse> buildBadRequestErrorResponse(
      String field, String code, String message) {
    return buildErrorResponse(field, code, message, HttpStatus.BAD_REQUEST);
  }

  private ResponseEntity<ErrorResponse> buildErrorResponse(
      String field, String code, String message, HttpStatus httpStatus) {
    ErrorResponse errorResponse = new ErrorResponse(List.of(new FieldError(field, code, message)));
    return ResponseEntity.status(httpStatus).body(errorResponse);
  }
}
