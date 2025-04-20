package com.antharos.corporateorganization.application.commands.employee.terminate;

import com.antharos.corporateorganization.domain.user.User;
import com.antharos.corporateorganization.domain.user.UserId;
import com.antharos.corporateorganization.domain.user.UserNotFoundException;
import com.antharos.corporateorganization.domain.user.repository.MessageProducer;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TerminateEmployeeCommandHandler {

  private final UserRepository userRepository;

  private final MessageProducer messageProducer;

  public void doHandle(final TerminateEmployeeCommand command) {
    final UserId userId = UserId.of(command.getUserId());

    User user = this.userRepository
        .findBy(userId)
            .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

    user.terminate(command.getModificationUser());
    this.userRepository.save(user);
    this.messageProducer.sendUserTerminatedEvent(user);
  }
}
