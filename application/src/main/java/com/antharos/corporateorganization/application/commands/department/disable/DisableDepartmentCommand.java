package com.antharos.corporateorganization.application.commands.department.disable;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class DisableDepartmentCommand {
  String departmentId;
  String user;
}
