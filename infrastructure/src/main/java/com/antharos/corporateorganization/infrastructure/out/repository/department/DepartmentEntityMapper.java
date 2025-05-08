package com.antharos.corporateorganization.infrastructure.out.repository.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.valueobject.EmployeeId;
import com.antharos.corporateorganization.infrastructure.out.repository.employee.EmployeeEntity;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentEntityMapper {
  default Department toDomain(final DepartmentEntity entity) {

    return new Department(
        DepartmentId.of(entity.getId().toString()),
        entity.getDescription(),
        entity.getDepartmentHead() != null
            ? new Employee(EmployeeId.of(entity.getDepartmentHead().getId().toString()))
            : null,
        entity.isActive(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getLastModifiedBy(),
        entity.getLastModifiedAt());
  }

  default DepartmentEntity toEntity(final Department domain) {
    final DepartmentEntity entity = new DepartmentEntity();

    entity.setId(UUID.fromString(domain.getId().getValueAsString()));
    entity.setDescription(domain.getDescription());
    if (domain.getDepartmentHead() != null) {
      EmployeeEntity e = new EmployeeEntity();
      e.setId(UUID.fromString(domain.getDepartmentHead().getId().getValueAsString()));
      entity.setDepartmentHead(e);
    }
    entity.setActive(domain.isActive());
    entity.setCreatedBy(domain.getCreatedBy());
    entity.setCreatedAt(domain.getCreatedAt());
    entity.setLastModifiedBy(domain.getLastModifiedBy());
    entity.setLastModifiedAt(domain.getLastModifiedAt());

    return entity;
  }
}
