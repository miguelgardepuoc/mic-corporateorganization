package com.antharos.corporateorganization.infrastructure.apirest;

import com.antharos.corporateorganization.application.create.HireEmployeeCommand;
import com.antharos.corporateorganization.application.create.HireEmployeeCommandHandler;
import com.antharos.corporateorganization.application.find.FindEmployeesQuery;
import com.antharos.corporateorganization.application.find.FindEmployeesQueryHandler;
import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.employee.EmployeeMapper;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.employee.EmployeeResponse;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.employee.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final HireEmployeeCommandHandler hireEmployeeCommandHandler;
  private final FindEmployeesQueryHandler findEmployeesQueryHandler;

  private final EmployeeMapper employeeMapper;

  @PostMapping("/hiring")
  public ResponseEntity<User> hireEmployee(@RequestBody UserDTO request) {
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
            .createdBy("admin")
            .build();

    this.hireEmployeeCommandHandler.doHandle(command);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @GetMapping
  public ResponseEntity<List<EmployeeResponse>> findEmployees() {
    return ResponseEntity.ok(
            this.employeeMapper.toEmployeeResponse(
                    this.findEmployeesQueryHandler.handle(FindEmployeesQuery.of()).stream().toList()));
  }
}
