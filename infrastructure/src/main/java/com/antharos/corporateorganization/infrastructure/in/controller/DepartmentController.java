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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@Tag(name = "Department", description = "Operations related to departments")
public class DepartmentController {

  private final FindDepartmentsQueryHandler findDepartmentsQueryHandler;
  private final RenameDepartmentCommandHandler renameDepartmentCommandHandler;
  private final DisableDepartmentCommandHandler disableDepartmentCommandHandler;
  private final CreateDepartmentCommandHandler createDepartmentCommandHandler;
  private final EditHeadDepartmentCommandHandler editHeadDepartmentCommandHandler;
  private final DepartmentMapper departmentMapper;

  @ManagementOnly
  @GetMapping
  @Operation(summary = "Get all departments", description = "Returns a list of all departments")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of departments",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = DepartmentResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<List<DepartmentResponse>> findDepartments() {
    return ResponseEntity.ok(
        this.departmentMapper.toDepartmentResponse(
            this.findDepartmentsQueryHandler.handle().stream().toList()));
  }

  @ManagementOnly
  @PatchMapping("/{id}/renaming")
  @Operation(
      summary = "Rename a department",
      description = "Changes the description of a department")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Department renamed successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
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
  @Operation(summary = "Disable a department", description = "Marks the department as disabled")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Department disabled"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<Void> disableDepartment(@PathVariable String id) {
    DisableDepartmentCommand command = DisableDepartmentCommand.builder().departmentId(id).build();

    this.disableDepartmentCommandHandler.handle(command);
    return ResponseEntity.noContent().build();
  }

  @ManagementOnly
  @PostMapping("")
  @Operation(summary = "Create a department", description = "Creates a new department")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Department created"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
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
  @Operation(
      summary = "Update department head",
      description = "Assigns a new head to the department")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Head updated successfully"),
        @ApiResponse(responseCode = "404", description = "Department not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
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
