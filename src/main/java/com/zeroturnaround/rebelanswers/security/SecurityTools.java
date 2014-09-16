package com.zeroturnaround.rebelanswers.security;

import com.zeroturnaround.rebelanswers.domain.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityTools {

  private SecurityTools() {
  }

  public static User getAuthenticatedUser() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth != null) {
      Object principal = auth.getPrincipal();
      if (principal instanceof UserDetailsWrapper) {
        return ((UserDetailsWrapper) principal).getDelegate();
      }
    }

    return null;
  }
}