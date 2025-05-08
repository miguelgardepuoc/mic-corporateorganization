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
import com.antharos.corporateorganization.infrastructure.in.dto.employee.EmployeeAuthResponse;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.EmployeeMapper;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.EmployeeResponse;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.HireEmployeeRequest;
import com.antharos.corporateorganization.infrastructure.in.util.AuditorUtils;
import com.antharos.corporateorganization.infrastructure.security.ManagementOnly;
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
  public ResponseEntity<EmployeeAuthResponse> getEmployeeByUsername(@PathVariable String username) {
    Optional<Employee> employee =
        this.findByUsername.handle(
            FindEmployeeByUsernameQuery.builder().username(username).build());

    return employee
        .map(emp -> ResponseEntity.ok(this.employeeMapper.toEmployeeAuthResponse(emp)))
        .orElse(ResponseEntity.notFound().build());
  }

  @ManagementOnly
  @GetMapping("/{employeeId}")
  public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable String employeeId) {
    Optional<Employee> employee =
        this.findById.handle(FindEmployeeByIdQuery.builder().id(employeeId).build());

    return employee
        .map(emp -> ResponseEntity.ok(this.employeeMapper.toEmployeeResponse(emp)))
        .orElse(ResponseEntity.notFound().build());
  }
}
