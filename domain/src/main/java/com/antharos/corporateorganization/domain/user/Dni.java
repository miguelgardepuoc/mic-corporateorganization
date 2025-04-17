package com.antharos.corporateorganization.domain.user;

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
      throw new IllegalArgumentException("Invalid DNI format");
    }
    return new Dni(dni);
  }
}
