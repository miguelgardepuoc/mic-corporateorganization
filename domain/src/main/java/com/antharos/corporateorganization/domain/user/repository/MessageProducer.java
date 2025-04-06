package com.antharos.corporateorganization.domain.user.repository;

import com.antharos.corporateorganization.domain.user.User;
import java.util.UUID;

public interface MessageProducer {
  void sendMessage(UUID id, final String subject, User user);

  void sendUserHiredMessage(User user);
}
