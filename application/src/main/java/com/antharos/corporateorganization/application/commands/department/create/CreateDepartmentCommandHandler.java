package com.antharos.corporateorganization.application.commands.department.create;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentAlreadyExistsException;
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
              throw new DepartmentAlreadyExistsException();
            });

    Department newDepartment =
        Department.create(departmentId, command.getDescription(), command.getCreatedBy());

    this.departmentRepository.save(newDepartment);
  }
}
