package com.antharos.corporateorganization.infrastructure.in.controller;

import com.antharos.corporateorganization.application.commands.employee.hire.HireEmployeeCommand;
import com.antharos.corporateorganization.application.commands.employee.hire.HireEmployeeCommandHandler;
import com.antharos.corporateorganization.application.queries.employee.FindEmployeesQueryHandler;
import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.EmployeeMapper;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.EmployeeResponse;
import com.antharos.corporateorganization.infrastructure.in.dto.employee.HireEmployeeRequest;
import com.antharos.corporateorganization.infrastructure.in.util.AuditorUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final HireEmployeeCommandHandler hireEmployeeCommandHandler;
  private final FindEmployeesQueryHandler findEmployeesQueryHandler;
  private final AuditorUtils auditorUtils;
  private final EmployeeMapper employeeMapper;

  @PostMapping("/hiring")
  public ResponseEntity<User> hireEmployee(@RequestBody HireEmployeeRequest request) {
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
            .createdBy(this.auditorUtils.getCurrentUsername())
            .build();

    this.hireEmployeeCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> findEmployees() {
    return ResponseEntity.ok(
        this.employeeMapper.toEmployeeResponse(
            this.findEmployeesQueryHandler.handle().stream().toList()));
  }
}
