package com.antharos.corporateorganization.application.commands.employee.terminate;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EmployeeRepository;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TerminateEmployeeCommandHandler {

  private final EmployeeRepository employeeRepository;

  private final EventProducer eventProducer;

  public void doHandle(final TerminateEmployeeCommand command) {
    final EmployeeId employeeId = EmployeeId.of(command.getUserId());

    Employee employee =
        this.employeeRepository
            .findBy(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(command.getUserId()));

    employee.terminate(command.getModificationUser());
    this.employeeRepository.save(employee);
    this.eventProducer.sendEmployeeTerminatedEvent(employee);
  }
}
