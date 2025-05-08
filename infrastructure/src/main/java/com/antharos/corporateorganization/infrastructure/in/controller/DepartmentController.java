package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.commands.department.create.CreateDepartmentCommand;
import com.antharos.corporateorganization.application.commands.department.create.CreateDepartmentCommandHandler;
import com.antharos.corporateorganization.application.commands.department.disable.DisableDepartmentCommand;
import com.antharos.corporateorganization.application.commands.department.disable.DisableDepartmentCommandHandler;
import com.antharos.corporateorganization.application.commands.department.headedit.EditHeadDepartmentCommand;
import com.antharos.corporateorganization.application.commands.department.headedit.EditHeadDepartmentCommandHandler;
import com.antharos.corporateorganization.application.commands.department.rename.RenameDepartmentCommand;
import com.antharos.corporateorganization.application.commands.department.rename.RenameDepartmentCommandHandler;
import com.antharos.corporateorganization.application.queries.department.FindDepartmentsQueryHandler;
import com.antharos.corporateorganization.infrastructure.in.dto.department.*;
import com.antharos.corporateorganization.infrastructure.in.util.AuditorUtils;
import com.antharos.corporateorganization.infrastructure.security.ManagementOnly;
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
  private final EditHeadDepartmentCommandHandler editHeadDepartmentCommandHandler;
  private final DepartmentMapper departmentMapper;

  @ManagementOnly
  @GetMapping
  public ResponseEntity<List<DepartmentResponse>> findDepartments() {
    return ResponseEntity.ok(
        this.departmentMapper.toDepartmentResponse(
            this.findDepartmentsQueryHandler.handle().stream().toList()));
  }

  @ManagementOnly
  @PatchMapping("/{id}/renaming")
  public ResponseEntity<Void> renameDepartment(
      @PathVariable String id, @RequestBody RenameDepartmentRequest request) {
    RenameDepartmentCommand command =
        RenameDepartmentCommand.builder()
            .departmentId(id)
            .description(request.getDescription())
            .user(AuditorUtils.getCurrentUsername())
            .build();
    this.renameDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @ManagementOnly
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> disableDepartment(@PathVariable String id) {
    DisableDepartmentCommand command = DisableDepartmentCommand.builder().departmentId(id).build();
    this.disableDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @ManagementOnly
  @PostMapping("")
  public ResponseEntity<Void> createDepartment(@RequestBody CreateDepartmentRequest request) {
    CreateDepartmentCommand command =
        CreateDepartmentCommand.builder()
            .id(request.id())
            .description(request.description())
            .createdBy(AuditorUtils.getCurrentUsername())
            .build();

    this.createDepartmentCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @ManagementOnly
  @PutMapping("/{id}/head")
  public ResponseEntity<Void> updateDepartmentHead(
      @PathVariable String id, @RequestBody UpdateDepartmentHeadRequest request) {
    EditHeadDepartmentCommand command =
        EditHeadDepartmentCommand.builder()
            .id(id)
            .createdBy(AuditorUtils.getCurrentUsername())
            .username(request.username())
            .build();

    this.editHeadDepartmentCommandHandler.handle(command);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
