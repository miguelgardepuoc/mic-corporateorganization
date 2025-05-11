package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.commands.employee.hire.HireEmployeeCommand;
import com.antharos.corporateorganization.application.commands.employee.hire.HireEmployeeCommandHandler;
import com.antharos.corporateorganization.application.commands.employee.markasinactive.MarkEmployeeAsInactiveCommand;
import com.antharos.corporateorganization.application.commands.employee.markasinactive.MarkEmployeeAsInactiveCommandHandler;
import com.antharos.corporateorganization.application.commands.employee.putonleave.PutEmployeeOnLeaveCommand;
import com.antharos.corporateorganization.application.commands.employee.putonleave.PutEmployeeOnLeaveCommandHandler;
import com.antharos.corporateorganization.application.commands.employee.terminate.TerminateEmployeeCommand;
import com.antharos.corporateorganization.application.commands.employee.terminate.TerminateEmployeeCommandHandler;
import com.antharos.corporateorganization.application.queries.employee.*;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.*;
import com.antharos.corporateorganization.infrastructure.in.util.AuditorUtils;
import com.antharos.corporateorganization.infrastructure.security.ManagementOnly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.PermitAll;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@Tag(name = "Employee", description = "Operations related to employees")
public class EmployeeController {

  private final HireEmployeeCommandHandler hireEmployeeCommandHandler;
  private final FindEmployeesQueryHandler findEmployeesQueryHandler;
  private final FindEmployeeByUsernameQueryHandler findByUsername;
  private final FindEmployeeByIdQueryHandler findById;
  private final TerminateEmployeeCommandHandler terminateEmployeeCommandHandler;
  private final PutEmployeeOnLeaveCommandHandler putEmployeeOnLeaveCommandHandler;
  private final MarkEmployeeAsInactiveCommandHandler markEmployeeAsInactiveCommandHandler;
  private final EmployeeMapper employeeMapper;

  @ManagementOnly
  @PostMapping("/hiring")
  @Operation(
      summary = "Hire a new employee",
      description = "Adds a new employee to the organization")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Employee hired successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid request"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<Employee> hireEmployee(@RequestBody HireEmployeeRequest request) {
    HireEmployeeCommand command =
        HireEmployeeCommand.builder()
            .userId(request.id())
            .dni(request.dni())
            .name(request.name())
            .surname(request.surname())
            .telephoneNumber(request.telephoneNumber())
            .salary(request.salary())
            .departmentId(request.departmentId())
            .hiringDate(request.hiringDate())
            .role(request.role())
            .jobTitleId(request.jobTitleId())
            .createdBy(AuditorUtils.getCurrentUsername())
            .build();

    this.hireEmployeeCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @ManagementOnly
  @GetMapping
  @Operation(summary = "List all employees", description = "Returns a list of all employees")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "List of employees",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EmployeeResponse.class))),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<List<EmployeeResponse>> findEmployees(Authentication authentication) {
    return ResponseEntity.ok(
        this.employeeMapper.toEmployeeResponse(
            this.findEmployeesQueryHandler
                .handle(
                    FindEmployeesQuery.builder()
                        .username(AuditorUtils.getCurrentUsername())
                        .build())
                .stream()
                .toList()));
  }

  @ManagementOnly
  @PatchMapping("/{id}/on-leave")
  @Operation(summary = "Put employee on leave", description = "Marks the employee as on leave")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee put on leave"),
        @ApiResponse(responseCode = "404", description = "Employee not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<Void> putUserOnLeave(@PathVariable String id) {
    PutEmployeeOnLeaveCommand command =
        PutEmployeeOnLeaveCommand.builder()
            .userId(id)
            .modificationUser(AuditorUtils.getCurrentUsername())
            .build();

    this.putEmployeeOnLeaveCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @ManagementOnly
  @PatchMapping("/{id}/termination")
  @Operation(
      summary = "Terminate employee",
      description = "Terminates the employment of the employee")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee terminated"),
        @ApiResponse(responseCode = "404", description = "Employee not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<Void> terminateUser(@PathVariable String id) {
    TerminateEmployeeCommand command =
        TerminateEmployeeCommand.builder()
            .userId(id)
            .modificationUser(AuditorUtils.getCurrentUsername())
            .build();

    this.terminateEmployeeCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @ManagementOnly
  @PatchMapping("/{id}/inactivation")
  @Operation(
      summary = "Mark employee as inactive",
      description = "Marks the employee record as inactive")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee marked as inactive"),
        @ApiResponse(responseCode = "404", description = "Employee not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<Void> markUserAsInactive(@PathVariable String id) {
    MarkEmployeeAsInactiveCommand command =
        MarkEmployeeAsInactiveCommand.builder()
            .userId(id)
            .modificationUser(AuditorUtils.getCurrentUsername())
            .build();

    this.markEmployeeAsInactiveCommandHandler.doHandle(command);
    return ResponseEntity.ok().build();
  }

  @PermitAll
  @GetMapping("/username/{username}")
  @Operation(
      summary = "Get employee by username",
      description = "Fetches employee details by username")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee found"),
        @ApiResponse(responseCode = "404", description = "Employee not found")
      })
  public ResponseEntity<EmployeeAuthResponse> getEmployeeByUsername(@PathVariable String username) {
    Optional<Employee> employee =
        this.findByUsername.handle(
            FindEmployeeByUsernameQuery.builder().username(username).build());

    return employee
        .map(emp -> ResponseEntity.ok(this.employeeMapper.toEmployeeAuthResponse(emp)))
        .orElse(ResponseEntity.notFound().build());
  }

  @ManagementOnly
  @GetMapping("/{id}")
  @Operation(summary = "Get employee by ID", description = "Fetches employee details by ID")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Employee found"),
        @ApiResponse(responseCode = "404", description = "Employee not found"),
        @ApiResponse(responseCode = "403", description = "Forbidden")
      })
  public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String employeeId) {
    Optional<Employee> employee =
        this.findById.handle(FindEmployeeByIdQuery.builder().id(employeeId).build());

    return employee
        .map(emp -> ResponseEntity.ok(this.employeeMapper.toEmployeeResponse(emp)))
        .orElse(ResponseEntity.notFound().build());
  }
}
