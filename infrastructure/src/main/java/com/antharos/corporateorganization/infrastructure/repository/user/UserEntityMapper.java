package com.antharos.corporateorganization.infrastructure.repository.user;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.jobtitle.JobTitle;
import com.antharos.corporateorganization.domain.user.*;
import com.antharos.corporateorganization.infrastructure.repository.department.DepartmentEntityMapper;
import com.antharos.corporateorganization.infrastructure.repository.jobtitle.JobTitleEntityMapper;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserEntityMapper {
  default User toDomain(final UserEntity entity) {
    return new User(
        UserId.of(String.valueOf(entity.getId())),
        new Dni(entity.getDni()),
        new Name(entity.getName()),
        new Surname(entity.getSurname()),
        new TelephoneNumber(entity.getTelephoneNumber()),
        new Department(entity.getId()),
        new Salary(entity.getSalary()),
        new HiringDate(entity.getHiringDate()),
        entity.getRole(),
        new JobTitle(entity.getJobTitle().getId()),
        entity.getCreatedBy());
  }

  default UserEntity toEntity(
      final User domain,
      final DepartmentEntityMapper departmentEntityMapper,
      final JobTitleEntityMapper jobTitleEntityMapper) {
    final UserEntity entity = new UserEntity();

    entity.setId(UUID.fromString(domain.getId().getValueAsString()));
    entity.setDni(domain.getDni().value());
    entity.setName(domain.getName().value());
    entity.setSurname(domain.getSurname().value());
    entity.setTelephoneNumber(domain.getTelephoneNumber().value());
    entity.setSalary(domain.getSalary().amount());
    entity.setRole(domain.getRole());
    entity.setDepartment(departmentEntityMapper.toEntity(domain.getDepartment()));
    entity.setHiringDate(domain.getHiringDate().value());
    entity.setEmployeeNumber(domain.getEmployeeNumber());
    entity.setCorporateEmail(domain.getCorporateEmail());
    entity.setUsername(domain.getUsername());
    entity.setPassword(domain.getPassword());
    entity.setStatus(domain.getStatus());
    entity.setJobTitle(jobTitleEntityMapper.toEntity(domain.getJobTitle()));
    entity.setCreatedBy(domain.getCreatedBy());
    entity.setCreatedAt(domain.getCreatedAt());
    entity.setLastModifiedBy(domain.getLastModifiedBy());
    entity.setLastModifiedAt(domain.getLastModifiedAt());

    return entity;
  }
}
