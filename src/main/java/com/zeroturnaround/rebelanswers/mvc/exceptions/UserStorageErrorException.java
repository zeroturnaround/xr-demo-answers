package com.zeroturnaround.rebelanswers.mvc.exceptions;

import com.zeroturnaround.rebelanswers.domain.User;

public class UserStorageErrorException extends RuntimeException {
  private final User user;

  public UserStorageErrorException(final User user) {
    this(user, null);
  }

  public UserStorageErrorException(final User user, final Throwable cause) {
    super("Unexpected error while storing the user '" + user + "'.", cause);
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}