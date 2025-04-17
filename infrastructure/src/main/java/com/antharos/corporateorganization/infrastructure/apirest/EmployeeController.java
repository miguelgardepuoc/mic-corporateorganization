package com.antharos.corporateorganization.infrastructure.apirest;

import com.antharos.corporateorganization.application.create.HireEmployeeCommand;
import com.antharos.corporateorganization.application.create.HireEmployeeCommandHandler;
import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.infrastructure.apirest.presentationmodel.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

  private final HireEmployeeCommandHandler hireEmployeeCommandHandler;

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
}
