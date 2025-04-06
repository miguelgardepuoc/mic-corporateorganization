package com.antharos.corporateorganization.infrastructure.apirest;

import com.antharos.corporateorganization.domain.globalexceptions.AlreadyExistsException;
import com.antharos.corporateorganization.domain.globalexceptions.NotFoundException;
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
}
