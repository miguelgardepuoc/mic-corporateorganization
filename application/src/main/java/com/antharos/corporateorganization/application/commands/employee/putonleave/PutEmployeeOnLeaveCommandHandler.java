package com.antharos.corporateorganization.application.commands.employee.putonleave;

import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.exception.EmployeeNotFoundException;
import com.antharos.corporateorganization.domain.employee.repository.EventProducer;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PutEmployeeOnLeaveCommandHandler {

  private final UserRepository userRepository;

  private final EventProducer eventProducer;

  public void doHandle(final PutEmployeeOnLeaveCommand command) {
    final EmployeeId employeeId = EmployeeId.of(command.getUserId());

    Employee employee =
        this.userRepository
            .findBy(employeeId)
            .orElseThrow(() -> new EmployeeNotFoundException(command.getUserId()));

    employee.putOnLeave(command.getModificationUser());
    this.userRepository.save(employee);
    this.eventProducer.sendEmployeePutOnLeaveEvent(employee);
  }
}
