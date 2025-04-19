package com.antharos.corporateorganization.infrastructure.in.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AuditorUtils {

  public String getCurrentUsername() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication != null
        && authentication.getPrincipal() instanceof UserDetails userDetails) {
      return userDetails.getUsername();
    }

    return "admin";
  }
}
