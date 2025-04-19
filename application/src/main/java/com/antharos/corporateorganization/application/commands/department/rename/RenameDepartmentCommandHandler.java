package com.antharos.corporateorganization.application.commands.department.rename;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class RenameDepartmentCommandHandler {

  private final DepartmentRepository departmentRepository;

  public void handle(RenameDepartmentCommand command) {
    final DepartmentId departmentId = DepartmentId.of(command.getDepartmentId());

    Department department =
        this.departmentRepository
            .findBy(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(command.getDepartmentId()));

    department.rename(command.getDescription(), command.getUser());
    this.departmentRepository.save(department);
  }
}
