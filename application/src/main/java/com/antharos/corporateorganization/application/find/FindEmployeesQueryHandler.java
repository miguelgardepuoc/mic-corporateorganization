package com.antharos.corporateorganization.application.find;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class FindEmployeesQueryHandler {
  private final UserRepository userRepository;

  public List<User> handle(FindEmployeesQuery findEmployeesQuery) {
    return this.userRepository.findAll();
  }
}
