package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel;

import com.antharos.corporateorganization.domain.department.Department;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);
}
