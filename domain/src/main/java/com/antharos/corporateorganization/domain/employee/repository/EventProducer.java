package com.antharos.corporateorganization.domain.employee.repository;

import com.antharos.corporateorganization.domain.employee.Employee;

public interface EventProducer {

  void sendEmployeeHiredEvent(Employee employee);

  void sendEmployeePutOnLeaveEvent(Employee employee);

  void sendEmployeeTerminatedEvent(Employee employee);

  void sendEmployeeMarkedAsInactiveEvent(Employee employee);
}
