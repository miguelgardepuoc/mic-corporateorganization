package com.antharos.corporateorganization.domain.user;

import lombok.Getter;

@Getter
public enum Role {
  DIRECCION_DE_LA_EMPRESA("Dirección de la empresa"),
  RESPONSABLE_DE_DEPARTAMENTO("Responsable de departamento"),
  EMPLEADO("Empleado");

  private final String description;

  Role(String description) {
    this.description = description;
  }
}
