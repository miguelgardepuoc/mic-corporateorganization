package com.antharos.corporateorganization.infrastructure.in.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuditorUtils {

  public static String getCurrentUsername() {

    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null) {
      return authentication.getPrincipal().toString();
    }

    return "admin";
  }
}
