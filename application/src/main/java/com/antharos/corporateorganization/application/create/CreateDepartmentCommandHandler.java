package com.antharos.corporateorganization.application.create;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentAlreadyExists;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateDepartmentCommandHandler {

  private final DepartmentRepository departmentRepository;

  public void doHandle(final CreateDepartmentCommand command) {
    final DepartmentId departmentId = DepartmentId.of(command.getId());

    this.departmentRepository
        .findBy(departmentId)
        .ifPresent(
            existing -> {
              throw new DepartmentAlreadyExists();
            });

    Department newDepartment =
        Department.create(departmentId, command.getDescription(), command.getCreatedBy());

    this.departmentRepository.save(newDepartment);
  }
}
