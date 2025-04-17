package com.antharos.corporateorganization.infrastructure.apirest.presentationmodel;

import com.antharos.corporateorganization.application.find.FindDepartmentsQuery;
import com.antharos.corporateorganization.application.find.FindDepartmentsQueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
  private final FindDepartmentsQueryHandler findDepartmentsQueryHandler;
  private final DepartmentMapper departmentMapper;

  @GetMapping
  public ResponseEntity<List<DepartmentResponse>> findDepartments() {
    return ResponseEntity.ok(
        this.departmentMapper.toDepartmentResponse(
            this.findDepartmentsQueryHandler.handle(FindDepartmentsQuery.of()).stream().toList()));
  }
}
