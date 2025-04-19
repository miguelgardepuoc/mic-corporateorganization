package com.antharos.corporateorganization.application.queries.employee;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class FindEmployeesQueryHandler {
  private final UserRepository userRepository;

  public List<User> handle() {
    return this.userRepository.findAll();
  }
}
