package com.antharos.corporateorganization.infrastructure.in.dto.department;

import com.antharos.corporateorganization.domain.department.Department;
import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.employee.Employee;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DepartmentMapper {
  @Mapping(target = "departmentHeadUserId", source = "departmentHead")
  DepartmentResponse toDepartmentResponse(Department department);

  List<DepartmentResponse> toDepartmentResponse(List<Department> departments);

  default String map(DepartmentId id) {
    return id == null ? null : id.getValueAsString();
  }

  default String map(Employee employee) {
    return employee == null ? null : employee.getId().getValueAsString();
  }
}
