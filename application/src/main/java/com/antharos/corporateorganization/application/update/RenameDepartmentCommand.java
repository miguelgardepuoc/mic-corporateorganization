package com.antharos.corporateorganization.application.update;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class RenameDepartmentCommand {
  String departmentId;
  String description;
  String user;
}
