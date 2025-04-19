package com.antharos.corporateorganization.infrastructure.repository.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.UserId;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentEntityMapper {
  default Department toDomain(final DepartmentEntity entity) {

    return new Department(
        entity.getId(),
        entity.getDescription(),
        entity.getDepartmentHead() != null
            ? new User(UserId.of(entity.getDepartmentHead().getId().toString()))
            : null,
        entity.isActive(),
        entity.getCreatedBy(),
        entity.getCreatedAt(),
        entity.getLastModifiedBy(),
        entity.getLastModifiedAt());
  }

  default DepartmentEntity toEntity(final Department domain) {
    final DepartmentEntity entity = new DepartmentEntity();

    entity.setId(domain.getId());
    entity.setDescription(domain.getDescription());
    entity.setActive(domain.isActive());
    entity.setCreatedBy(domain.getCreatedBy());
    entity.setCreatedAt(domain.getCreatedAt());
    entity.setLastModifiedBy(domain.getLastModifiedBy());
    entity.setLastModifiedAt(domain.getLastModifiedAt());

    return entity;
  }
}
