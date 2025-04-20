package com.antharos.corporateorganization.application.commands.employee.terminate;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class TerminateEmployeeCommand {
  String userId;
  String modificationUser;
}
