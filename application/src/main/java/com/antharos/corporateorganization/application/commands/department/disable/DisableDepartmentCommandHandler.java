package com.antharos.corporateorganization.application.commands.department.disable;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DisableDepartmentCommandHandler {

  private final DepartmentRepository departmentRepository;

  public void handle(DisableDepartmentCommand command) {
    final DepartmentId departmentId = DepartmentId.of(command.getDepartmentId());

    final Department department =
        this.departmentRepository
            .findBy(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(command.getDepartmentId()));

    department.remove(command.getUser());
    this.departmentRepository.save(department);
  }
}
