package com.antharos.corporateorganization.application.create;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.department.DepartmentNotFoundException;
import com.antharos.corporateorganization.domain.department.DepartmentRepository;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleNotFoundException;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleRepository;
import com.antharos.corporateorganization.domain.user.*;
import com.antharos.corporateorganization.domain.user.repository.MessageProducer;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class HireEmployeeCommandHandler {

  private final UserRepository userRepository;

  private final DepartmentRepository departmentRepository;

  private final JobTitleRepository jobTitleRepository;

  private final MessageProducer messageProducer;

  public void doHandle(final HireEmployeeCommand command) {
    final UserId userId = UserId.of(command.getUserId());

    this.userRepository
        .findBy(userId)
        .ifPresent(
            existing -> {
              throw new UserAlreadyExists();
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

    final Dni dni = new Dni(command.getDni());
    final Name name = new Name(command.getName());
    final Surname surname = new Surname(command.getSurname());
    final TelephoneNumber telephoneNumber = new TelephoneNumber(command.getTelephoneNumber());
    final Salary salary = new Salary(command.getSalary());
    final HiringDate hiringDate = new HiringDate(command.getHiringDate());

    final User user =
        User.create(
            userId,
            dni,
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

    this.userRepository.save(user);
    this.messageProducer.sendUserHiredMessage(user);
  }
}
