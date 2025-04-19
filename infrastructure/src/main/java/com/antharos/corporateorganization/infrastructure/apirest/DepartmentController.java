package com.antharos.corporateorganization.infrastructure.apirest;

import com.antharos.corporateorganization.application.create.CreateDepartmentCommand;
import com.antharos.corporateorganization.application.create.CreateDepartmentCommandHandler;
import com.antharos.corporateorganization.application.find.FindDepartmentsQueryHandler;
import com.antharos.corporateorganization.application.update.DisableDepartmentCommand;
import com.antharos.corporateorganization.application.update.DisableDepartmentCommandHandler;
import com.antharos.corporateorganization.application.update.RenameDepartmentCommand;
import com.antharos.corporateorganization.application.update.RenameDepartmentCommandHandler;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department.CreateDepartmentRequest;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department.DepartmentMapper;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department.DepartmentResponse;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.department.RenameDepartmentRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentController {
  private final FindDepartmentsQueryHandler findDepartmentsQueryHandler;
  private final RenameDepartmentCommandHandler renameDepartmentCommandHandler;
  private final DisableDepartmentCommandHandler disableDepartmentCommandHandler;
  private final CreateDepartmentCommandHandler createDepartmentCommandHandler;
  private final DepartmentMapper departmentMapper;

  @GetMapping
  public ResponseEntity<List<DepartmentResponse>> findDepartments() {
    return ResponseEntity.ok(
        this.departmentMapper.toDepartmentResponse(
            this.findDepartmentsQueryHandler.handle().stream().toList()));
  }

  @PatchMapping("/{id}/renaming")
  public ResponseEntity<Void> renameDepartment(
      @PathVariable String id, @RequestBody RenameDepartmentRequest request) {
    RenameDepartmentCommand command =
        RenameDepartmentCommand.builder()
            .departmentId(id)
            .description(request.getNewName())
            .user("admin")
            .build();
    this.renameDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> disableDepartment(@PathVariable String id) {
    DisableDepartmentCommand command = DisableDepartmentCommand.builder().departmentId(id).build();
    this.disableDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("")
  public ResponseEntity<Void> createDepartment(@RequestBody CreateDepartmentRequest request) {
    CreateDepartmentCommand command =
        CreateDepartmentCommand.builder()
            .id(request.id())
            .description(request.description())
            .createdBy("admin")
            .build();

    this.createDepartmentCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }
}
