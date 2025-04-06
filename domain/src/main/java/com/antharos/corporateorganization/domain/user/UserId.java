package com.antharos.corporateorganization.domain.user;

import java.util.UUID;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class UserId {

  String valueAsString;

  public static UserId of(String userId) {
    try {
      UUID.fromString(userId);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(
          "UserId must be a valid UUID. Invalid string value: " + userId);
    }
    return new UserId(userId);
  }
}
