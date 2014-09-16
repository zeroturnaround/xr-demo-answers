package com.zeroturnaround.rebelanswers.mvc.exceptions;

import com.zeroturnaround.rebelanswers.domain.Question;

public class QuestionStorageErrorException extends RuntimeException {
  private final Question question;

  public QuestionStorageErrorException(final Question question) {
    this(question, null);
  }

  public QuestionStorageErrorException(final Question question, final Throwable cause) {
    super("Unexpected error while storing the question '" + question + "'.", cause);
    this.question = question;
  }

  public Question getQuestion() {
    return question;
  }
}