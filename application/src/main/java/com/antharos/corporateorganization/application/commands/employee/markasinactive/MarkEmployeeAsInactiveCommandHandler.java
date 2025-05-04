package com.antharos.corporateorganization.application.commands.employee.markasinactive;

import com.antharos.corporateorganization.domain.employee.*;
import com.antharos.corporateorganization.domain.employee.event.EmployeeMarkedAsInactiveEvent;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkEmployeeAsInactiveCommandHandler {

  private final UserRepository userRepository;

  private final EventProducer eventProducer;

  public void doHandle(final MarkEmployeeAsInactiveCommand command) {
    final EmployeeId employeeId = EmployeeId.of(command.getUserId());

    Employee employee =
        this.userRepository
            .findBy(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(command.getUserId()));

    employee.markAsInactive(command.getModificationUser(), eventProducer);
    this.userRepository.save(employee);

    for (Object event : employee.getDomainEvents()) {
      if (event instanceof EmployeeMarkedAsInactiveEvent(Employee newEmployee)) {
        this.eventProducer.sendEmployeeMarkedAsInactiveEvent(newEmployee);
      }
    }
    employee.clearDomainEvents();
  }
}
