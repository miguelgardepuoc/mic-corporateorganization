package com.antharos.corporateorganization.application.commands.employee.putonleave;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PutEmployeeOnLeaveCommand {
  String userId;
  String modificationUser;
}
