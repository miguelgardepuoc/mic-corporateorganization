package com.antharos.corporateorganization.application.commands.department.headedit;

import com.antharos.corporateorganization.domain.DepartmentManagementService;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EditHeadDepartmentCommandHandler {

  private final DepartmentManagementService departmentManagementService;

  public void handle(EditHeadDepartmentCommand command) {
    final DepartmentId departmentId = DepartmentId.of(command.getId());
    this.departmentManagementService.assignDepartmentHead(
        departmentId, command.getUsername(), command.getCreatedBy());
  }
}
