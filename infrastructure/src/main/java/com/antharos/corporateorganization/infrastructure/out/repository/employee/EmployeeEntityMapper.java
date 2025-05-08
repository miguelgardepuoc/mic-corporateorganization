package com.antharos.corporateorganization.infrastructure.out.repository.employee;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.employee.*;
import com.antharos.corporateorganization.domain.employee.valueobject.*;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.jobtitle.JobTitleId;
import com.antharos.corporateorganization.infrastructure.out.repository.department.DepartmentEntity;
import com.antharos.corporateorganization.infrastructure.out.repository.department.DepartmentEntityMapper;
import com.antharos.corporateorganization.infrastructure.out.repository.jobtitle.JobTitleEntityMapper;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeEntityMapper {

  default Employee toDomain(EmployeeEntity entity) {
    return Employee.builder()
        .id(EmployeeId.of(entity.getId().toString()))
        .employeeNumber(entity.getEmployeeNumber())
        .username(entity.getUsername())
        .dni(Dni.of(entity.getDni()))
        .name(new Name(entity.getName()))
        .surname(new Surname(entity.getSurname()))
        .telephoneNumber(new TelephoneNumber(entity.getTelephoneNumber()))
        .department(new Department(DepartmentId.of(entity.getDepartment().getId().toString())))
        .salary(new Salary(entity.getSalary()))
        .hiringDate(new HiringDate(entity.getHiringDate()))
        .role(entity.getRole())
        .jobTitle(new JobTitle(JobTitleId.of(String.valueOf(entity.getJobTitle().getId()))))
        .password(entity.getPassword())
        .corporateEmail(entity.getCorporateEmail())
        .status(entity.getStatus())
        .createdBy(entity.getCreatedBy())
        .createdAt(entity.getCreatedAt())
        .lastModifiedBy(entity.getLastModifiedBy())
        .lastModifiedAt(entity.getLastModifiedAt())
        .build();
  }

  default EmployeeEntity toEntity(
      Employee domain,
      DepartmentEntityMapper departmentEntityMapper,
      JobTitleEntityMapper jobTitleEntityMapper) {
    EmployeeEntity entity = new EmployeeEntity();
    entity.setId(UUID.fromString(domain.getId().getValueAsString()));
    entity.setEmployeeNumber(domain.getEmployeeNumber());
    entity.setUsername(domain.getUsername());
    entity.setDni(domain.getDni().getValueAsString());
    entity.setName(domain.getName().value());
    entity.setSurname(domain.getSurname().value());
    entity.setTelephoneNumber(domain.getTelephoneNumber().value());
    entity.setSalary(domain.getSalary().amount());
    entity.setHiringDate(domain.getHiringDate().value());
    entity.setRole(domain.getRole());
    entity.setPassword(domain.getPassword());
    entity.setCorporateEmail(domain.getCorporateEmail());
    entity.setStatus(domain.getStatus());
    entity.setCreatedBy(domain.getCreatedBy());
    entity.setCreatedAt(domain.getCreatedAt());
    entity.setLastModifiedBy(domain.getLastModifiedBy());
    entity.setLastModifiedAt(domain.getLastModifiedAt());
    entity.setDepartment(
        new DepartmentEntity(UUID.fromString(domain.getDepartment().getId().getValueAsString())));
    entity.setJobTitle(jobTitleEntityMapper.toEntity(domain.getJobTitle()));
    return entity;
  }
}
