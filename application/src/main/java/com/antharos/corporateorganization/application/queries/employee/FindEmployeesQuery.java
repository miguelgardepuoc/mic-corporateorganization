package com.antharos.corporateorganization.application.queries.employee;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class FindEmployeesQuery {
  String username;
}
