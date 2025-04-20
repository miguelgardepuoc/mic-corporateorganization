package com.antharos.corporateorganization.application.commands.employee.markasinactive;

import com.antharos.corporateorganization.domain.user.*;
import com.antharos.corporateorganization.domain.user.repository.MessageProducer;
import com.antharos.corporateorganization.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MarkEmployeeAsInactiveCommandHandler {

  private final UserRepository userRepository;

  private final MessageProducer messageProducer;

  public void doHandle(final MarkEmployeeAsInactiveCommand command) {
    final UserId userId = UserId.of(command.getUserId());

    User user = this.userRepository
        .findBy(userId)
            .orElseThrow(() -> new UserNotFoundException(command.getUserId()));

    user.markAsInactive(command.getModificationUser());
    this.userRepository.save(user);
    this.messageProducer.sendUserMarkedAsInactiveEvent(user);
  }
}
