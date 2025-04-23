package com.antharos.corporateorganization.application.commands.employee.hire;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.employee.*;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeAlreadyExists;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.*;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleNotFoundException;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HireEmployeeCommandHandler {

  private final UserRepository userRepository;

  private final DepartmentRepository departmentRepository;

  private final JobTitleRepository jobTitleRepository;

  private final EventProducer eventProducer;

  public void doHandle(final HireEmployeeCommand command) {
    final EmployeeId employeeId = EmployeeId.of(command.getUserId());

    this.userRepository
        .findBy(employeeId)
        .ifPresent(
            existing -> {
              throw new EmployeeAlreadyExists();
            });

    final DepartmentId departmentId = DepartmentId.of(command.getDepartmentId());

    Department department =
        this.departmentRepository
            .findBy(departmentId)
            .orElseThrow(() -> new DepartmentNotFoundException(command.getDepartmentId()));

    final JobTitleId jobTitleId = JobTitleId.of(command.getJobTitleId());

    JobTitle jobTitle =
        this.jobTitleRepository
            .findBy(jobTitleId)
            .orElseThrow(() -> new JobTitleNotFoundException(command.getJobTitleId()));

    final Long lastEmployeeNumber =
        this.userRepository
            .findTopByOrderByEmployeeNumberDesc()
            .map(Employee::getEmployeeNumber)
            .orElse(0L);

    final Long newEmployeeNumber = lastEmployeeNumber + 1;
    final Name name = new Name(command.getName());
    final Surname surname = new Surname(command.getSurname());
    final TelephoneNumber telephoneNumber = new TelephoneNumber(command.getTelephoneNumber());
    final Salary salary = new Salary(command.getSalary());
    final HiringDate hiringDate = new HiringDate(command.getHiringDate());

    final Employee employee =
        Employee.hire(
            employeeId,
            newEmployeeNumber,
            Dni.of(command.getDni()),
            name,
            surname,
            telephoneNumber,
            salary,
            department,
            hiringDate,
            command.getRole(),
            jobTitle,
            command.getCreatedBy(),
            this.userRepository);

    this.userRepository.save(employee);
    this.eventProducer.sendEmployeeHiredEvent(employee);
  }
}
