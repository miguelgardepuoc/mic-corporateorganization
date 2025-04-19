package com.antharos.corporateorganization.application.update;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DisableDepartmentCommand {
  String departmentId;
  String user;
}
