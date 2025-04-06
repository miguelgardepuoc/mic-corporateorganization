package com.antharos.corporateorganization.infrastructure.event.model;

import com.antharos.corporateorganization.domain.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserDomainMessage extends BaseMessage<User> {

  public UserDomainMessage(String id, String subject, User content) {
    super(id, subject, content);
  }
}
