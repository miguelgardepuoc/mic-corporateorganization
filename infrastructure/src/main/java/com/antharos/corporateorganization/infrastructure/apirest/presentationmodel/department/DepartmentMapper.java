package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);

  default UUID map(DepartmentId id) {
    return id == null ? null : UUID.fromString(id.getValueAsString());
  }
}
