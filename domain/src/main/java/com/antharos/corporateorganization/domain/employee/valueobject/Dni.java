package com.antharos.corporateorganization.domain.employee.valueobject;

import com.antharos.corporateorganization.domain.employee.exception.InvalidDniException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Dni {

  String valueAsString;

  public static Dni of(String dni) {
    Objects.requireNonNull(dni, "DNI cannot be null");
    if (!dni.matches("\\d{8}[A-Za-z]")) {
      throw new InvalidDniException("Invalid DNI format");
    }
    return new Dni(dni);
  }
}
