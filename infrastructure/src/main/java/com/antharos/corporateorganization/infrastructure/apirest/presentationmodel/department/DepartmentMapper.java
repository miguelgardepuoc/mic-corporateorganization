package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department;

import com.antharos.corporateorganization.domain.department.Department;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);
}
