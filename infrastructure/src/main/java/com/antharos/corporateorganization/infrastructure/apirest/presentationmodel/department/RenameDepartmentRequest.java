package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RenameDepartmentRequest {
  private String newName;
}
