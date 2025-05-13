package com.antharos.corporateorganization.application.commands.department.headedit;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.department.exception.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.UsernameNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class EditHeadDepartmentCommandHandler {

  private final DepartmentRepository departmentRepository;
  private final UserRepository userRepository;

  public void handle(EditHeadDepartmentCommand command) {
    final DepartmentId departmentId = DepartmentId.of(command.getId());

    final Department department =
        this.departmentRepository
            .findBy(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(command.getId()));

    final Employee employee =
        this.userRepository
            .findByUsername(command.getUsername())
            .orElseThrow(() -> new UsernameNotFoundException(command.getId()));

    department.updateDepartmentHead(employee, command.getCreatedBy(), this.userRepository);
    this.departmentRepository.save(department);
    this.userRepository.save(employee);
  }
}
