package com.antharos.corporateorganization.application.queries.employee;

import static com.antharos.corporateorganization.domain.employee.Role.ROLE_DEPARTMENT_HEAD;

import com.antharos.corporateorganization.domain.department.DepartmentId;
import com.antharos.corporateorganization.domain.employee.Employee;
import com.antharos.corporateorganization.domain.employee.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeesQueryHandler {
  private final UserRepository userRepository;

  public List<Employee> handle(FindEmployeesQuery query) {
    Optional<Employee> userOpt = this.userRepository.findByUsername(query.getUsername());

    if (userOpt.isPresent()) {
      Employee user = userOpt.get();

      if (ROLE_DEPARTMENT_HEAD.equals(user.getRole())) {
        DepartmentId departmentId = user.getDepartment().getId();
        return this.userRepository.findByDepartmentId(departmentId);
      }
      return this.userRepository.findAll();
    } else {
      throw new UsernameNotFoundException(query.getUsername());
    }
  }
}
