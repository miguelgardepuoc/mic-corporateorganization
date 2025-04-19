package com.antharos.corporateorganization.application.commands.department.create;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class CreateDepartmentCommand {
  String id;
  String description;
  String createdBy;
}
