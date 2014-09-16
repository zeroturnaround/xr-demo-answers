package com.zeroturnaround.rebelanswers.mvc.exceptions;

import com.zeroturnaround.rebelanswers.domain.Comment;

public class CommentStorageErrorException extends RuntimeException {
  private final Comment comment;

  public CommentStorageErrorException(final Comment comment) {
    this(comment, null);
  }

  public CommentStorageErrorException(final Comment comment, final Throwable cause) {
    super("Unexpected error while storing the comment '" + comment + "'.", cause);
    this.comment = comment;
  }

  public Comment getComment() {
    return comment;
  }
}