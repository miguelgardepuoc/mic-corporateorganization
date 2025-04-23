package com.antharos.corporateorganization.infrastructure.out.event.model;

import com.antharos.corporateorganization.domain.employee.Employee;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeePayloadMapper {

  default EmployeePayload toPayload(Employee domain) {
    EmployeePayload payload = new EmployeePayload();
    payload.setId(UUID.fromString(domain.getId().getValueAsString()));
    payload.setEmployeeNumber(domain.getEmployeeNumber());
    payload.setUsername(domain.getUsername());
    payload.setDni(domain.getDni().getValueAsString());
    payload.setName(domain.getName().value());
    payload.setSurname(domain.getSurname().value());
    payload.setTelephoneNumber(domain.getTelephoneNumber().value());
    payload.setSalary(domain.getSalary().amount());
    payload.setHiringDate(domain.getHiringDate().value());
    payload.setRole(domain.getRole());
    payload.setCorporateEmail(domain.getCorporateEmail());
    payload.setStatus(domain.getStatus());
    payload.setDepartmentId(
        domain.getDepartment() != null ? domain.getDepartment().getId().getValueAsString() : null);
    payload.setJobTitleId(
        domain.getJobTitle() != null ? domain.getJobTitle().getId().getValueAsString() : null);
    return payload;
  }
}
